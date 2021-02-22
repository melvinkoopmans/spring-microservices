package com.thoughtmechanix.zuulserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ZuulServerApplication {

  @Bean
  public Sampler defaultSampler() {
    return new AlwaysSampler();
  }

  public static void main(String[] args) {
    SpringApplication.run(ZuulServerApplication.class, args);
  }
}
