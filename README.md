# API Gateway实现比较

比较不同的API Gateway实现，分为三大类：

* 通用反向代理：Nginx、Haproxy
* 网络编程框架：Netty、Spring Webflux
* API Gateway框架：Spring Cloud Gateway、Zuul2

## Benchmark

先准备三台配置一样的机器，分别用来运行Tomcat、API Gateway、Gatling。

其中Tomcat和API Gateway的配置得是一样的，Gatling的配置要稍好一些。

修改服务器的 /etc/security/limits.conf，添加以下内容：

```txt
*            soft    nofile          65536
*            hard    nofile          200000
*            soft    core            unlimited
*            hard    core            unlimited
*            soft    sigpending      90000
*            hard    sigpending      90000
*            soft    nproc           90000
*            hard    nproc           90000
```

然后重启服务器，运行`ulimit -a`看看是否生效：

```txt
core file size          (blocks, -c) unlimited
pending signals                 (-i) 90000
open files                      (-n) 65536
max user processes              (-u) 90000
```

每次启动一个API Gateway，访问 http://<api-gateway-ip>:9090 看看是否能够看到Tomcat的首页。

然后用Gatling压。

### 启动Tomcat

执行以下命令：

```bash
docker run -p 8080:8080 \
  -p 1099:1099 \
  -p 1100:1100 \
  -d \
  --name tomcat \
  -e HEAP_SIZE="1G" \
  -e CONNECTOR_MAX_THREADS="1000" \
  -e CONNECTOR_MIN_SPARE_THREADS="1000" \
  -e EANBLE_ACCESS_LOG_VALVE="false" \
  chanjarster/api-gateway-comp-tomcat
```

### 启动Nginx

执行以下命令：

```bash
docker run -p 9090:80 \
  -d \
  --name nginx \
  --add-host tomcat:<tomcat-ip> \ 
  chanjarster/api-gateway-comp-nginx
```

把上面的`<tomcat-ip>`成Tomcat所在服务器的地址。

### 启动Haproxy

执行以下命令：

```bash
docker run -p 9090:80 \
  -d \
  --name haproxy \
  --add-host tomcat:<tomcat-ip> \ 
  chanjarster/api-gateway-comp-haproxy
```

把上面的`<tomcat-ip>`成Tomcat所在服务器的地址。

### 启动Netty

执行下列命令：

```bash
docker run -p 9090:8080 \
  -p 1099:1099 \
  -p 1100:1100 \
  -d \
  --name netty \
  --add-host tomcat:<tomcat-ip> \
  -e JAVA_OPTS="-DsocketType=EPOLL" \
  -e HEAP_SIZE="2G" \
  chanjarster/api-gateway-comp-netty-proxy
```

socketType可以是EPOLL、KQUEUE、NIO（默认）

### 启动Zuul2

执行下列命令：

```bash
docker run -p 9090:9090 \
  -p 1099:1099 \
  -p 1100:1100 \
  -d \
  --name zuul2 \
  --add-host tomcat:<tomcat-ip> \
  -e HEAP_SIZE="2G" \
  -e JAVA_OPTS="-Dzuul.server.netty.socket.epoll=true" \
  chanjarster/api-gateway-comp-zuul2
```

### 启动Spring Cloud Gateway

执行下列命令：

```bash
docker run -p 9090:9090 \
  -p 1099:1099 \
  -p 1100:1100 \
  -d \
  --name scg \
  --add-host tomcat:<tomcat-ip> \
  -e HEAP_SIZE="2G" \
  -e JAVA_OPTS="-Dreactor.netty.native=true"
  chanjarster/api-gateway-comp-scg
```

`reactor.netty.native`为true时使用epoll/kqueue，为false时则使用nio，默认为false。

### 启动Gatling

先在`/etc/hosts`下配置的host：

```txt
<api-gateway-ip> api-gateway
<tomcat-ip> tomcat
```

到[gatling](gatling)目录下，执行：

先压Tomcat获得基准数据，压的时候要观测Tomcat的JVM的CPU利用率，如果没有到~90%那么就加大线程数，直到Tomcat的CPU利用率能够到~90%，然后多压几次让JVM充分热身。

```bash
mvn gatling:test -Dgatling.simulationClass=Tomcat
```

再压API Gateway，记得在Run Description写上备注区分，比如：

```bash
mvn gatling:test -Dgatling.simulationClass=ApiGateway -Dgatling.runDescription=Nginx

mvn gatling:test -Dgatling.simulationClass=ApiGateway -Dgatling.runDescription=Haproxy

mvn gatling:test -Dgatling.simulationClass=ApiGateway -Dgatling.runDescription=Netty

mvn gatling:test -Dgatling.simulationClass=ApiGateway -Dgatling.runDescription=SpringCloudGateway

mvn gatling:test -Dgatling.simulationClass=ApiGateway -Dgatling.runDescription=Zuul2
```

压API Gateway的时候要记得观察Tomcat的CPU利用率，如果利用率比直压的低，那么就要考虑修改API Gateway的参数。

压Netty、SpringCloudGateway、Zuul2的时候要先预热几遍。

测完后到target/gatling目录下查看结果。

## 结果

我是在2核 4G上压的，结果在[benchmark-results](benchmark-results)目录下。

## 题外话

### Nginx

Nginx的参数调优参考的这两篇文章：

* [NGINX Tuning For Best Performance](https://github.com/denji/nginx-tuning)
* [Tuning NGINX for Performance](https://www.nginx.com/blog/tuning-nginx/)

### Haproxy

Haproxy的参数调优参考的这篇文章：

* [Performance Tuning HAProxy](https://blog.codeship.com/performance-tuning-haproxy/)

其中有一个很重要的参数`nbproc`，这个默认值是1，当你有多核CPU的时候可以设置更大。这个参数核Nginx的`worker_processes`类似，但是没有办法像Nginx那样设置为`auto`。

### Netty

Netty proxy例子的代码来自于[Proxy Server](https://netty.io/4.1/xref/io/netty/example/proxy/package-summary.html)

### Zuul2

[zuul2](zuul2)的例子代码copy自[Zuul2的官方例子](https://github.com/Netflix/zuul/tree/2.1/zuul-sample)，做了一下改动：

1. 把gradle项目改成了maven项目
1. 把Filter从groovy改成了java
1. 去掉了动态加载groovy的配置

在把gradle改成maven的过程中发现许多依赖都是runtime的，所以直接在Maven里这样做是不行的：

```xml
<dependency>
  <groupId>com.netflix.zuul</groupId>
  <artifactId>zuul-core</artifactId>
  <version>2.1.4</version>
</dependency>
```

运行的时候你会发现缺这个缺那个。

解决办法就是把这些runtime依赖编程compile写到pom.xml里，做法如下：

1. 用`mvn dependency:list | grep runtime`把runtime依赖找出来
1. 把这些依赖写到pom.xml里，scope改成compile（不写scope就是compile）

运行一下看看，会出现ClassNotFoundException或者NoClassDefFoundError，解决办法如下：

1. 下载zuul2官方仓库，https://github.com/Netflix/zuul
1. 用IDE导入zuul2项目，查找提示缺少的类在哪个包里
1. 把这个依赖添加到pom.xml里

在运行一下，会出现NoSuchMethodError，这个错误说明类存在但是没有这个方法，就是说引用的版本不对，解决办法如下：

1. zuul2项目所在目录，到zuul-sample目录下，执行`../gradlew dependencies > dep.txt`得到依赖清单
1. 在IDE里找出现错误的类在哪个包里
1. 到dep.txt里看这个包是什么版本的
1. 修改pom.xml里的版本
