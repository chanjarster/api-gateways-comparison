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

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

public class HexDumpProxyFrontendHandler extends ChannelInboundHandlerAdapter {

  private final String remoteHost;
  private final int remotePort;

  // As we use inboundChannel.eventLoop() when building the Bootstrap this does not need to be volatile as
  // the backendChannel will use the same EventLoop (and therefore Thread) as the inboundChannel.
  private Channel backendChannel;

  public HexDumpProxyFrontendHandler(String remoteHost, int remotePort) {
    this.remoteHost = remoteHost;
    this.remotePort = remotePort;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    final Channel frontendChannel = ctx.channel();

    // Start the connection attempt.
    Bootstrap b = new Bootstrap();
    b
      .group(frontendChannel.eventLoop())
      .channel(ctx.channel().getClass())
      .handler(new HexDumpProxyBackendHandler(frontendChannel))
      .option(ChannelOption.AUTO_READ, false);
    ChannelFuture f = b.connect(remoteHost, remotePort);

    backendChannel = f.channel();
    f.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
          // connection complete start to read first data
          frontendChannel.read();
        } else {
          // Close the connection if the connection attempt has failed.
          frontendChannel.close();
        }
      }
    });
  }

  @Override
  public void channelRead(final ChannelHandlerContext ctx, Object msg) {
    if (backendChannel.isActive()) {
      backendChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
          if (future.isSuccess()) {
            // was able to flush out data, start to read the next chunk
            ctx.channel().read();
          } else {
            future.channel().close();
          }
        }
      });
    }
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    if (backendChannel != null) {
      closeOnFlush(backendChannel);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    closeOnFlush(ctx.channel());
  }

  /**
   * Closes the specified channel after all queued write requests are flushed.
   */
  static void closeOnFlush(Channel ch) {
    if (ch.isActive()) {
      ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
  }
}
