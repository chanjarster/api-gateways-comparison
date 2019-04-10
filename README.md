# API Gateway实现比较

比较不同的API Gateway实现，分为两大类：

* 通用型：Nginx、Netty
* 专用型：Spring Cloud Gateway、Zuul2

## Benchmark

### 准备Tomcat

弄一台机器，下载一个Tomcat 8.5。

修改server.xml，修改maxThreads、minSpareThreads（我用的是2000）

新建文件`$TOMCAT_HOME/bin/setenv.sh`，内容`CATALINA_OPTS=-Xms1G -Xmx1G`


### 启动API Gateway

弄一台机器启动不同类型的API Gateway，每次启动一个，然后用Gatling压。

在这台机器的`/etc/hosts`下添加Tomcat服务器的host：

#### Nginx

到[nginx](nginx)目录下，执行以下命令：

```bash
docker run -p 9090:80 \
  --rm \
  --add-host tomcat:<tomcat-ip> \ 
  chanjarster/api-gateway-comp-nginx
```

把上面的`<tomcat-ip>`成Tomcat所在服务器的地址。

然后访问 http://localhost:9090 看看是否能够看到Tomcat的首页。

#### Netty

执行下列命令：

```bash
docker run -p 9090:8080 \
  --rm \ 
  --add-host tomcat:<tomcat-ip> \
  -e JAVA_OPTS="-DsocketType=EPOLL" \
  -e HEAP_SIZE="1G" \
  chanjarster/api-gateway-comp-netty-proxy
```

socketType可以是EPOLL、KQUEUE、NIO（默认）

#### Zuul2

执行下列命令：

```bash
docker run -p 9090:9090 \
  --rm \
  --add-host tomcat:<tomcat-ip> \
  -e HEAP_SIZE="1G" \
  chanjarster/api-gateway-comp-zuul2
```

#### Spring Cloud Gateway

执行下列命令：

```bash
docker run -p 9090:9090 \
  --rm \
  --add-host tomcat:<tomcat-ip> \
  -e HEAP_SIZE="1G" \
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

```bash
mvn gatling:test
```

先压Tomcat，然后压API Gateway，记得在Run Description写上备注区分。

测完后到target/gatling目录下查看结果。


## 题外话

### Netty

Netty proxy例子的代码来自于[Proxy Server](https://netty.io/4.1/xref/io/netty/example/proxy/package-summary.html)

### Zuul2

[zuul2](zuul2)的例子代码copy自[Zuul2的官方例子](https://github.com/Netflix/zuul/tree/2.1/zuul-sample)，不过改成了maven项目。

在改造过程中发现Zuul2是用Gradle构建的，许多依赖都是runtime的，所以直接在Maven里这样做是不行的：

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
