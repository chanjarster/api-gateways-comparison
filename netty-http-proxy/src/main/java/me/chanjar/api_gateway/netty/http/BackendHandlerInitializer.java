package me.chanjar.api_gateway.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class BackendHandlerInitializer extends ChannelInitializer<Channel> {

  private static final String ENABLE_LOG = System.getProperty("enableLog", "false").toUpperCase();

  private Channel frontendChannel;

  public BackendHandlerInitializer(Channel frontendChannel) {
    this.frontendChannel = frontendChannel;
  }

  @Override
  protected void initChannel(Channel ch) throws Exception {
    if (ENABLE_LOG.equalsIgnoreCase("true")) {
      ch.pipeline().addLast(new SimpleLoggingHandler("backend"));
    }
    ch.pipeline().addLast(new HttpRequestEncoder());
    ch.pipeline().addLast(new BackendHandler(frontendChannel));
  }

}
