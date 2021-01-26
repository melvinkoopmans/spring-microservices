package com.thoughtmechanix.licenses;

import com.thoughtmechanix.licenses.model.Organization;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

  @Autowired
  RestTemplate restTemplate;

  public Organization getOrganization(String organizationId) {
    ResponseEntity<Organization> restExchange = restTemplate.exchange(
        "http://organizations/v1/organizations/{organizationId}",
        HttpMethod.GET,
        null, Organization.class, organizationId
    );

    return restExchange.getBody();
  }
}
