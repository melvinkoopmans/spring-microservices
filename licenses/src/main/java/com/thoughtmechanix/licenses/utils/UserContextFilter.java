package com.thoughtmechanix.licenses.utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserContextFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException { }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));

    logger.info("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

    chain.doFilter(httpServletRequest, response);
  }

  @Override
  public void destroy() { }
}
