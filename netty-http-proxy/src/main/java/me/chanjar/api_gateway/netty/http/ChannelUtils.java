package me.chanjar.api_gateway.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

public class ChannelUtils {

  public static void closeOnFlush(Channel channel) {
    if (channel == null) {
      return;
    }
    if (channel.isActive()) {
      channel
        .writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE)
      ;
    }
  }
}
