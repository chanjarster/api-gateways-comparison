package me.chanjar.api_gateway.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class FrontendHandlerInitializer extends ChannelInitializer<SocketChannel> {

  private static final String ENABLE_LOG = System.getProperty("enableLog", "false").toUpperCase();

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    if (ENABLE_LOG.equalsIgnoreCase("true")) {
      ch.pipeline().addLast(new SimpleLoggingHandler("frontend"));
    }

    ch.pipeline().addLast(new HttpRequestDecoder());
    ch.pipeline().addLast(new FrontendHandler());
  }

}
