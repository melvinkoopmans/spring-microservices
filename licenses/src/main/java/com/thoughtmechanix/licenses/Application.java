package com.thoughtmechanix.licenses;

import com.thoughtmechanix.common.SharedConfiguration;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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

  @Autowired
  private ServiceConfig serviceConfig;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(serviceConfig.getRedisHostname());
    jedisConnectionFactory.setPort(serviceConfig.getRedisPort());

    return jedisConnectionFactory;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(jedisConnectionFactory());

    return template;
  }

  @LoadBalanced
  @Bean
  @Qualifier("oAuth2ClientContext")
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
