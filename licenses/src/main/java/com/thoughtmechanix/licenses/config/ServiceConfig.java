package com.thoughtmechanix.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
  @Value("${example.property}")
  private String exampleProperty;

  @Value("${redis.hostname}")
  private String redisHostname;

  @Value("${redis.port}")
  private Integer redisPort;

  public String getExampleProperty() {
    return exampleProperty;
  }

  public String getRedisHostname() {
    return redisHostname;
  }

  public Integer getRedisPort() {
    return redisPort;
  }
}
