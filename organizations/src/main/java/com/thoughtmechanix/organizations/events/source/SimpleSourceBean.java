package com.thoughtmechanix.organizations.events.source;

import com.thoughtmechanix.common.UserContextHolder;
import com.thoughtmechanix.organizations.events.models.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

  private Source source;

  public static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

  public SimpleSourceBean(Source source) {
    this.source = source;
  }

  public void publishOrgChange(String action, String orgId) {
    logger.debug("Sending Kafka message {} for organization id: {}", action, orgId);

    OrganizationChangeModel change = new OrganizationChangeModel(
        OrganizationChangeModel.class.getTypeName(),
        action,
        orgId,
        UserContextHolder.getContext().getCorrelationId()
    );

    source
        .output()
        .send(
            MessageBuilder
              .withPayload(change)
              .build()
        );
  }
}
