package com.thoughtmechanix.common;

import java.util.Collections;
import java.util.List;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.thoughtmechanix.common")
public class SharedConfiguration {

  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate template = new RestTemplate();

    List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
    
    if (interceptors == null) {
      template.setInterceptors(
          Collections.singletonList(new UserContextInterceptor())
      );
    } else {
      interceptors.add(new UserContextInterceptor());
      template.setInterceptors(interceptors);
    }

    return template;
  }
}
