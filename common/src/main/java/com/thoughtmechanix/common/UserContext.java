package com.thoughtmechanix.common;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
  public static final String CORRELATION_ID = "tmx-correlation-id";
  public static final String AUTH_TOKEN = "Authorization";

  private String correlationId = "";
  private String authToken = "";

  public String getCorrelationId() {
    return correlationId;
  }

  public UserContext setCorrelationId(String correlationId) {
    this.correlationId = correlationId;

    return this;
  }

  public String getAuthToken() {
    return authToken;
  }

  public UserContext setAuthToken(String authToken) {
    this.authToken = authToken;

    return this;
  }
}
