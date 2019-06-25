package me.chanjar.api_gateway.netty.http;

import io.netty.channel.*;

public class BackendHandler extends ChannelInboundHandlerAdapter {

  private final Channel frontendChannel;

  public BackendHandler(Channel frontendChannel) {
    this.frontendChannel = frontendChannel;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.channel().read();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    frontendChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          ctx.channel().read();
        } else {
          future.channel().close();
        }
      }
    });

  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ChannelUtils.closeOnFlush(frontendChannel);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ChannelUtils.closeOnFlush(ctx.channel());
  }
}
