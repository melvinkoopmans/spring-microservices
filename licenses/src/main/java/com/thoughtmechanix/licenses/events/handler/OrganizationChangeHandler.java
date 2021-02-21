package com.thoughtmechanix.licenses.events.handler;

import com.thoughtmechanix.licenses.events.CustomChannels;
import com.thoughtmechanix.licenses.events.model.OrganizationChangeModel;
import com.thoughtmechanix.licenses.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannels.class)
public class OrganizationChangeHandler {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

  @Autowired
  OrganizationRedisRepository redisRepository;

  @StreamListener("inboundOrganizationChanges")
  public void handle(OrganizationChangeModel organizationChangeModel) {
    logger.debug(
        "Received an event for organization id {}",
        organizationChangeModel.getOrganizationId()
    );

    switch (organizationChangeModel.getAction()) {
      case "SAVE":
        logger.debug(
            "Removing organization {} from Redis cache.",
            organizationChangeModel.getOrganizationId()
        );
        redisRepository.removeOrganization(organizationChangeModel.getOrganizationId());
        break;
      default:
        logger.debug("Unknown action {}", organizationChangeModel.getAction());
    }
  }
}
