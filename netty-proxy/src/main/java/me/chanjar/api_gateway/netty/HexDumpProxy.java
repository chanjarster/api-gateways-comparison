/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package me.chanjar.api_gateway.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class HexDumpProxy {

  static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "9090"));
  static final String REMOTE_HOST = System.getProperty("remoteHost", "tomcat");
  static final int REMOTE_PORT = Integer.parseInt(System.getProperty("remotePort", "8080"));

  static final String SOCKET_TYPE = System.getProperty("socketType", "NIO").toUpperCase();

  public static void main(String[] args) throws Exception {
    System.err.println("Proxying *:" + LOCAL_PORT + " to " + REMOTE_HOST + ':' + REMOTE_PORT + " ...");

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
    case "KQUEUE":
      bossGroup = new KQueueEventLoopGroup(1);
      workerGroup = new KQueueEventLoopGroup();
      channelClass = KQueueServerSocketChannel.class;
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
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new HexDumpProxyInitializer(REMOTE_HOST, REMOTE_PORT))
          .childOption(ChannelOption.AUTO_READ, false)
          .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
