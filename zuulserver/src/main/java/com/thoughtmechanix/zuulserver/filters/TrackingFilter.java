package com.thoughtmechanix.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The tracking filter adds a correlation ID to the request headers.
 * This ID can be used for tracking requests as they journey through many microservices.
 */
@Component
public class TrackingFilter extends ZuulFilter {

  public static final int FILTER_ORDER = 1;
  public static final boolean SHOULD_FILTER = true;
  public static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);
  private final FilterUtils filterUtils;

  public TrackingFilter(FilterUtils filterUtils) {
    this.filterUtils = filterUtils;
  }

  @Override
  public String filterType() {
    return FilterUtils.PRE_FILTER_TYPE;
  }

  @Override
  public int filterOrder() {
    return FILTER_ORDER;
  }

  @Override
  public boolean shouldFilter() {
    return SHOULD_FILTER;
  }

  public boolean isCorrelationIdPresent() {
    return filterUtils.getCorrelationId() != null;
  }

  public String generateCorrelationId() {
    return UUID.randomUUID().toString();
  }

  @Override
  public Object run() {
    if (isCorrelationIdPresent()) {
      logger.debug("tmx-correlation-id found in tracking filter: {}.", filterUtils.getCorrelationId());
    } else {
      filterUtils.setCorrelationId(generateCorrelationId());
      logger.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
    }

    RequestContext ctx = RequestContext.getCurrentContext();
    logger.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());

    return null;
  }
}
