package com.thoughtmechanix.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

  @Value("${signing.key}")
  private String signingKey;

  public String getSigningKey() {
    return signingKey;
  }
}
