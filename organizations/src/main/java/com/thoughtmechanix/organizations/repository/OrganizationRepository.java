package com.thoughtmechanix.organizations.repository;

import com.thoughtmechanix.organizations.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
