package com.thoughtmechanix.organizations.controller;

import com.thoughtmechanix.organizations.model.Organization;
import com.thoughtmechanix.organizations.repository.OrganizationRepository;
import com.thoughtmechanix.organizations.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

  @Autowired
  private OrganizationRepository repository;

  @Autowired
  private OrganizationService organizationService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Iterable<Organization> getOrganizations() {
    return repository.findAll();
  }

  @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
  public Organization getOrganization(@PathVariable(name = "organizationId") String organizationId) {
    return repository.findOne(organizationId);
  }

  @RequestMapping(value = "/{organizationId}", method = RequestMethod.POST)
  public Organization updateOrganization(
      @RequestBody Organization organizationDTO,
      @PathVariable String organizationId
  ) {
    Organization organization = repository.findOne(organizationId);
    organization.setContactName(organizationDTO.getContactName());
    organizationService.save(organization);

    return organization;
  }
}
