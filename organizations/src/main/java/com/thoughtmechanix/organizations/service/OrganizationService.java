package com.thoughtmechanix.organizations.service;

import com.thoughtmechanix.organizations.events.source.SimpleSourceBean;
import com.thoughtmechanix.organizations.model.Organization;
import com.thoughtmechanix.organizations.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

  private final OrganizationRepository repository;

  private final SimpleSourceBean simpleSourceBean;

  public OrganizationService(OrganizationRepository repository, SimpleSourceBean simpleSourceBean) {
    this.repository = repository;
    this.simpleSourceBean = simpleSourceBean;
  }

  public void save(Organization organization) {
    repository.save(organization);
    simpleSourceBean.publishOrgChange("SAVE", organization.getOrganizationId());
  }
}
