package com.thoughtmechanix.licenses.clients;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.Utils;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.OrganizationRedisRepository;
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
  OrganizationRedisRepository redisRepository;

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

    Organization organization = getCachedOrganization(organizationId);

    if (organization != null) {
      logger.debug("Using cached version of organization {}", organizationId);

      return organization;
    }

    logger.debug("Making call to organizations through Zuul.");

    ResponseEntity<Organization> restExchange = restTemplate.exchange(
        "http://zuulserver/api/organizations/v1/organizations/{organizationId}",
        HttpMethod.GET,
        null, Organization.class, organizationId
    );

    logger.debug("Zuul replied with status code {}", restExchange.getStatusCode());

    organization = restExchange.getBody();
    this.cacheOrganization(organization);

    return organization;
  }

  private Organization buildFallbackOrganization(String organizationId) {
    return new Organization()
        .setOrganizationId(organizationId)
        .setName("Fallback organization");
  }

  private Organization getCachedOrganization(String organizationId) {
    Organization organization;

    try {
      organization = redisRepository.findOrganization(organizationId);
    } catch (Exception e) {
      logger.debug("Unable to fetch organization {} from cache", organizationId);

      return null;
    }

    if (organization == null) {
      logger.debug("Organization not found in cache.");
    } else {
      logger.debug("Found organization {} in Redis cache.", organizationId);
    }

    return organization;
  }

  private void cacheOrganization(Organization organization) {
    try {
      redisRepository.saveOrganization(organization);
    } catch (Exception e) {
      logger.debug(
          "Failed to cache organization {} to Redis.\nException: {}",
          organization.getOrganizationId(),
          e.getMessage()
      );
    }
  }
}
