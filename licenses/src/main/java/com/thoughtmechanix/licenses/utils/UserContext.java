package com.thoughtmechanix.licenses.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
  public static final String CORRELATION_ID = "tmx-correlation-id";

  private String correlationId = "";

  public String getCorrelationId() {
    return correlationId;
  }

  public UserContext setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
    return this;
  }
}
