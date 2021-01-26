package com.thoughtmechanix.organizations.controller;

import com.thoughtmechanix.organizations.model.Organization;
import com.thoughtmechanix.organizations.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

  @Autowired
  private OrganizationRepository repository;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Iterable<Organization> getOrganizations() {
    return repository.findAll();
  }

  @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
  public Organization getOrganization(@PathVariable(name = "organizationId") String organizationId) {
    return repository.findOne(organizationId);
  }
}
