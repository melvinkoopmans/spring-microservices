package com.thoughtmechanix.licenses;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

  @HystrixCommand(fallbackMethod = "buildFallbackOrganization")
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
