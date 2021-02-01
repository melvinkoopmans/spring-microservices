package com.thoughtmechanix.licenses;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

  @Autowired
  RestTemplate restTemplate;

  @HystrixCommand(
      fallbackMethod = "buildFallbackOrganization",
      threadPoolKey = "organizationThreadPool",
      threadPoolProperties =
          {@HystrixProperty(name = "coreSize", value = "30"),
          @HystrixProperty(name = "maxQueueSize", value="10")},
      commandProperties =
          {@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="10"),
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="75"),
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="7000"),
          @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
          @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}
  )
  public Organization getOrganization(String organizationId) {
    Utils.randomlyRunLong();

    ResponseEntity<Organization> restExchange = restTemplate.exchange(
        "http://organizations/v1/organizations/{organizationId}",
        HttpMethod.GET,
        null, Organization.class, organizationId
    );

    return restExchange.getBody();
  }

  private Organization buildFallbackOrganization(String organizationId) {
    return new Organization()
        .setOrganizationId(organizationId)
        .setName("Fallback organization");
  }
}
