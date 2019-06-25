package me.chanjar.api_gateway.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpProxy {

  static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "9090"));

  static final String SOCKET_TYPE = System.getProperty("socketType", "NIO").toUpperCase();

  public static void main(String[] args) throws InterruptedException {

    System.err.println("Proxying *:" + LOCAL_PORT);

    // Configure the bootstrap.
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    Class channelClass = null;

    switch (SOCKET_TYPE) {
    case "NIO":
      bossGroup = new NioEventLoopGroup(1);
      workerGroup = new NioEventLoopGroup();
      channelClass = NioServerSocketChannel.class;
      break;
    case "EPOLL":
      bossGroup = new EpollEventLoopGroup(1);
      workerGroup = new EpollEventLoopGroup();
      channelClass = EpollServerSocketChannel.class;
      break;
    }

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
        .channel(channelClass)
        .childHandler(new FrontendHandlerInitializer())
        .childOption(ChannelOption.AUTO_READ, false)
        .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }

  }

}
