package me.chanjar.api_gateway.reactornetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import reactor.netty.tcp.TcpServer;

public class ReactorNettyProxy {

  static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "9090"));
  static final String REMOTE_HOST = System.getProperty("remoteHost", "tomcat");
  static final int REMOTE_PORT = Integer.parseInt(System.getProperty("remotePort", "8080"));

  public static void main(String[] args) throws InterruptedException {

    System.err.println("Proxying *:" + LOCAL_PORT + " to " + REMOTE_HOST + ':' + REMOTE_PORT + " ...");

    TcpServer tcpServer = TcpServer
      .create()
      .port(LOCAL_PORT);

    tcpServer
      .doOnConnection(connection -> {

        final Channel frontendChannel = connection.channel();

        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture backendChannelFuture = bootstrap
          .group(frontendChannel.eventLoop())
          .channel(frontendChannel.getClass())
          .remoteAddress(REMOTE_HOST, REMOTE_PORT)
          .option(ChannelOption.AUTO_READ, false)
          .handler(new ProxyBackendHandler(frontendChannel))
          .connect();

        Channel backendChannel = backendChannelFuture.channel();
        backendChannelFuture.addListener(future -> {
          if (future.isSuccess()) {
            frontendChannel.read();
          } else {
            frontendChannel.close();
          }
        });

        connection.addHandler(new ProxyFrontendHandler(REMOTE_HOST, REMOTE_PORT, backendChannel));
      })
      .bindNow()
      .onDispose()
      .block()
    ;

  }
}
