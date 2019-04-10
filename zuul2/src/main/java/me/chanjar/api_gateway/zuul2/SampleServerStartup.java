/*
 * Copyright 2018 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package me.chanjar.api_gateway.zuul2;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.config.DynamicIntProperty;
import com.netflix.discovery.EurekaClient;
import com.netflix.netty.common.accesslog.AccessLogPublisher;
import com.netflix.netty.common.channel.config.ChannelConfig;
import com.netflix.netty.common.channel.config.CommonChannelConfigKeys;
import com.netflix.netty.common.metrics.EventLoopGroupMetrics;
import com.netflix.netty.common.proxyprotocol.StripUntrustedProxyHeadersHandler;
import com.netflix.netty.common.status.ServerStatusManager;
import com.netflix.spectator.api.Registry;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.FilterUsageNotifier;
import com.netflix.zuul.RequestCompleteHandler;
import com.netflix.zuul.context.SessionContextDecorator;
import com.netflix.zuul.netty.server.BaseServerStartup;
import com.netflix.zuul.netty.server.DirectMemoryMonitor;
import com.netflix.zuul.netty.server.ZuulServerChannelInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Sample Server Startup - class that configures the Netty server startup settings
 * Author: Arthur Gonigberg
 * Date: November 20, 2017
 */
@Singleton
public class SampleServerStartup extends BaseServerStartup {

  @Inject
  public SampleServerStartup(ServerStatusManager serverStatusManager, FilterLoader filterLoader,
      SessionContextDecorator sessionCtxDecorator, FilterUsageNotifier usageNotifier,
      RequestCompleteHandler reqCompleteHandler, Registry registry,
      DirectMemoryMonitor directMemoryMonitor, EventLoopGroupMetrics eventLoopGroupMetrics,
      EurekaClient discoveryClient, ApplicationInfoManager applicationInfoManager,
      AccessLogPublisher accessLogPublisher) {
    super(serverStatusManager, filterLoader, sessionCtxDecorator, usageNotifier, reqCompleteHandler, registry,
        directMemoryMonitor, eventLoopGroupMetrics, discoveryClient, applicationInfoManager,
        accessLogPublisher);
  }

  @Override
  protected Map<Integer, ChannelInitializer> choosePortsAndChannels(
      ChannelGroup clientChannels,
      ChannelConfig channelDependencies) {
    Map<Integer, ChannelInitializer> portsToChannels = new HashMap<>();

    int port = new DynamicIntProperty("zuul.server.port.main", 9090).get();

    ChannelConfig channelConfig = BaseServerStartup.defaultChannelConfig();

    channelConfig
        .set(CommonChannelConfigKeys.allowProxyHeadersWhen, StripUntrustedProxyHeadersHandler.AllowWhen.ALWAYS);
    channelConfig.set(CommonChannelConfigKeys.preferProxyProtocolForClientIp, false);
    channelConfig.set(CommonChannelConfigKeys.isSSlFromIntermediary, false);
    channelConfig.set(CommonChannelConfigKeys.withProxyProtocol, false);

    portsToChannels
        .put(port, new ZuulServerChannelInitializer(port, channelConfig, channelDependencies, clientChannels));
    logPortConfigured(port, null);

    return portsToChannels;
  }

}
