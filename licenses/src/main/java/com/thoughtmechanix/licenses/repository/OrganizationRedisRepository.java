package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;

public interface OrganizationRedisRepository {
  void saveOrganization(Organization organization);
  Organization findOrganization(String organization);
}
