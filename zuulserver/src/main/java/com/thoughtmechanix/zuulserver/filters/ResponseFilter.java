package com.thoughtmechanix.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResponseFilter extends ZuulFilter {
  public static final int FILTER_ORDER = 1;
  public static final boolean SHOULD_FILTER = true;
  public static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

  private FilterUtils filterUtils;

  public ResponseFilter(FilterUtils filterUtils) {
    this.filterUtils = filterUtils;
  }

  @Override
  public String filterType() {
    return FilterUtils.POST_FILTER_TYPE;
  }

  @Override
  public int filterOrder() {
    return FILTER_ORDER;
  }

  @Override
  public boolean shouldFilter() {
    return SHOULD_FILTER;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();

    logger.debug(
        "Adding the correlation id to the outbound headers. {}",
        filterUtils.getCorrelationId()
    );

    ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());

    return null;
  }
}
