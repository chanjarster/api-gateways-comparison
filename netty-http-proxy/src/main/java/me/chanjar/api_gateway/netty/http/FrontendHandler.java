package me.chanjar.api_gateway.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class FrontendHandler extends ChannelInboundHandlerAdapter {

  private Channel backendChannel;

  private ChannelFuture backendChannelFuture;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.channel().read();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    if (msg instanceof HttpRequest) {

      handleHttpRequest(ctx, (HttpRequest) msg);

    } else if (msg instanceof HttpContent) {

      handleHttpContent(ctx, (HttpContent) msg);

    } else {
      ctx.fireChannelRead(msg);
    }

  }

  private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest msg) {

    Channel frontendChannel = ctx.channel();

    Bootstrap b = new Bootstrap();
    b
      .group(frontendChannel.eventLoop())
      .channel(frontendChannel.getClass())
      .handler(new BackendHandlerInitializer(frontendChannel))
      .option(ChannelOption.AUTO_READ, false);

    SocketAddress socketAddress = resolveBackendAddress(msg);
    processHeaders(msg);
    processUri(msg);

    backendChannelFuture = b.connect(socketAddress);
    backendChannel = backendChannelFuture.channel();

    backendChannelFuture.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {

        if (future.isSuccess()) {
          writeBackend(frontendChannel, msg);
        } else {
          frontendChannel.close();
        }
        future.removeListener(this);
      }
    });

  }

  protected SocketAddress resolveBackendAddress(HttpRequest msg) {
    return new InetSocketAddress("tomcat", 8080);
  }

  protected void processHeaders(HttpRequest msg) {
    msg.headers().set(HttpHeaderNames.HOST, "tomcat:8080");
  }

  protected void processUri(HttpRequest msg) {
  }

  private void handleHttpContent(ChannelHandlerContext ctx, HttpContent msg) {

    final Channel frontendChannel = ctx.channel();
    if (backendChannel.isActive()) {

      writeBackend(frontendChannel, msg);

    } else {

      backendChannelFuture.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if (future.isSuccess()) {
            writeBackend(frontendChannel, msg);
          } else {
            ReferenceCountUtil.release(msg);
          }
          future.removeListener(this);
        }
      });

    }

  }

  private void writeBackend(Channel frontendChannel, HttpObject frontendMsg) {

    backendChannel.writeAndFlush(frontendMsg).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          frontendChannel.read();
        } else {
          future.channel().close();
        }
      }
    });

  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ChannelUtils.closeOnFlush(backendChannel);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ChannelUtils.closeOnFlush(ctx.channel());
  }

}
