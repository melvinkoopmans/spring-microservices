package com.thoughtmechanix.licenses;

import com.thoughtmechanix.common.SharedConfiguration;
import com.thoughtmechanix.licenses.events.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableCircuitBreaker
@EnableResourceServer
@EnableBinding(Sink.class)
@Import(SharedConfiguration.class)
public class Application {

  @LoadBalanced
  @Bean
  @Qualifier("oauth2ClientContext")
  public OAuth2RestTemplate oAuth2RestTemplate(
      OAuth2ClientContext oAuth2ClientContext,
      OAuth2ProtectedResourceDetails details
  ) {
    return new OAuth2RestTemplate(details, oAuth2ClientContext);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
