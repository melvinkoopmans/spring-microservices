package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;

public interface OrganizationRedisRepository {
  void saveOrganization(Organization organization);
  void removeOrganization(String organizationId);
  Organization findOrganization(String organization);
}
