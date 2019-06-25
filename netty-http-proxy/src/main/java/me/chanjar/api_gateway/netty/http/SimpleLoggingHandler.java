package me.chanjar.api_gateway.netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SimpleLoggingHandler extends LoggingHandler {
  public SimpleLoggingHandler() {
  }

  public SimpleLoggingHandler(LogLevel level) {
    super(level);
  }

  public SimpleLoggingHandler(Class<?> clazz) {
    super(clazz);
  }

  public SimpleLoggingHandler(Class<?> clazz, LogLevel level) {
    super(clazz, level);
  }

  public SimpleLoggingHandler(String name) {
    super(name);
  }

  public SimpleLoggingHandler(String name, LogLevel level) {
    super(name, level);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (logger.isEnabled(internalLevel)) {
      logger.log(internalLevel, format(ctx, "READ"));
    }
    ctx.fireChannelRead(msg);
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    if (logger.isEnabled(internalLevel)) {
      logger.log(internalLevel, format(ctx, "WRITE"));
    }
    ctx.write(msg, promise);
  }

}
