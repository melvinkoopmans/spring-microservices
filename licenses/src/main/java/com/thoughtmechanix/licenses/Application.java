package com.thoughtmechanix.licenses;

import com.thoughtmechanix.common.SharedConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableCircuitBreaker
@Import(SharedConfiguration.class)
public class Application {

  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
