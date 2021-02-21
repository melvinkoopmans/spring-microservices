package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {

  public static final String HASH_NAME = "organization";

  private final HashOperations<String, String, Organization> hashOperations;

  public OrganizationRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void saveOrganization(Organization organization) {
    hashOperations.put(HASH_NAME, organization.getOrganizationId(), organization);
  }

  @Override
  public void removeOrganization(String organizationId) {
    hashOperations.delete(HASH_NAME, organizationId);
  }

  @Override
  public Organization findOrganization(String organizationId) {
    return hashOperations.get(HASH_NAME, organizationId);
  }
}
