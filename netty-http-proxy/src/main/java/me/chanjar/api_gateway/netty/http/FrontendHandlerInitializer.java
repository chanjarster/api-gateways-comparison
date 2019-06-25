package me.chanjar.api_gateway.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class FrontendHandlerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
//    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
    ch.pipeline().addLast(new HttpRequestDecoder());
    ch.pipeline().addLast(new FrontendHandler());
  }

}
