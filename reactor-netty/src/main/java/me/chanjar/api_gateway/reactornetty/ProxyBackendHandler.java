package me.chanjar.api_gateway.reactornetty;

import io.netty.channel.*;

public class ProxyBackendHandler extends ChannelInboundHandlerAdapter {

  private Channel frontendChannel;

  public ProxyBackendHandler(Channel frontendChannel) {
    this.frontendChannel = frontendChannel;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.read();
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
    ChannelClose.closeOnFlush(frontendChannel);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ChannelClose.closeOnFlush(ctx.channel());
  }

}
