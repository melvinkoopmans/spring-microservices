package com.thoughtmechanix.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtmechanix.licenses.Utils;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

  private final ServiceConfig config;
  private final LicenseRepository licenseRepository;

  private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

  @Autowired
  public LicenseService(ServiceConfig config, LicenseRepository repository) {
    this.config = config;
    this.licenseRepository = repository;
  }

  public License getLicense(String organizationId, String licenseId) {
    License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
    return license.withComment(config.getExampleProperty());
  }

  @HystrixCommand
  public List<License> getLicenseByOrg(String organizationId) {
    Utils.randomlyRunLong();

    logger.info("LicenseService Correlation id (Hystrix): {}", UserContextHolder.getContext().getCorrelationId());

    return licenseRepository.findByOrganizationId(organizationId);
  }

  public void saveLicense(License license) {
    license.withId(UUID.randomUUID().toString());
    licenseRepository.save(license);
  }
}
