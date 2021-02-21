package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.events.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

@Service
public class StreamLogger {

  private static final Logger logger = LoggerFactory.getLogger(StreamLogger.class);

  @StreamListener(Sink.INPUT)
  public void loggerSink(OrganizationChangeModel organizationChangeModel) {
    logger.debug(
        "Received an event for organization id {}",
        organizationChangeModel.getOrganizationId()
    );
  }
}
