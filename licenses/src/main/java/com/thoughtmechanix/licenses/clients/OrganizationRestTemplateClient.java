package com.thoughtmechanix.licenses.clients;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.Utils;
import com.thoughtmechanix.licenses.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrganizationRestTemplateClient {

  Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

  @Autowired
  OAuth2RestTemplate restTemplate;

  /**
   * TODO: Pass SecurityContext to thread, so we don't have to use semaphore.
   */
  @HystrixCommand(
      fallbackMethod = "buildFallbackOrganization",
      threadPoolKey = "organizationThreadPool",
      threadPoolProperties =
          {@HystrixProperty(name = "coreSize", value = "30"),
          @HystrixProperty(name = "maxQueueSize", value="10")},
      commandProperties =
          {@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="10"),
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="75"),
          @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="7000"),
          @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
          @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}
  )
  public Organization getOrganization(String organizationId) {
    Utils.randomlyRunLong();

    logger.debug("Making call to organizations through Zuul.");

    ResponseEntity<Organization> restExchange = restTemplate.exchange(
        "http://zuulserver/api/organizations/v1/organizations/{organizationId}",
        HttpMethod.GET,
        null, Organization.class, organizationId
    );

    logger.debug("Zuul replied with status code {}", restExchange.getStatusCode());

    return restExchange.getBody();
  }

  private Organization buildFallbackOrganization(String organizationId) {
    return new Organization()
        .setOrganizationId(organizationId)
        .setName("Fallback organization");
  }
}
