package me.chanjar.api_gateway.reactornetty;

import io.netty.channel.*;

public class ProxyFrontendHandler extends ChannelInboundHandlerAdapter {

  private String remoteHost;
  private int remotePort;

  private Channel backendChannel;

  public ProxyFrontendHandler(String remoteHost, int remotePort, Channel backendChannel) {
    this.remoteHost = remoteHost;
    this.remotePort = remotePort;
    this.backendChannel = backendChannel;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    if (backendChannel.isActive()) {

      backendChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
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

  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    if (backendChannel != null) {
      ChannelClose.closeOnFlush(backendChannel);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ChannelClose.closeOnFlush(ctx.channel());
  }

}
