package me.chanjar.api_gateway.zuul2.filters;

import com.netflix.zuul.context.SessionContext;
import com.netflix.zuul.filters.http.HttpInboundSyncFilter;
import com.netflix.zuul.message.http.HttpRequestMessage;
import com.netflix.zuul.netty.filter.ZuulEndPointRunner;

public class Routes extends HttpInboundSyncFilter {

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter(HttpRequestMessage httpRequestMessage) {
    return true;
  }

  @Override
  public HttpRequestMessage apply(HttpRequestMessage request) {
    SessionContext context = request.getContext();

    // Route healthchecks to the healthcheck endpoint.;
    context.setEndpoint(ZuulEndPointRunner.PROXY_ENDPOINT_FILTER_NAME);
    context.setRouteVIP("tomcat");

    return request;
  }
}
