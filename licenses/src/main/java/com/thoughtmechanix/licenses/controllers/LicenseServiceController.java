package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

  @Autowired
  private LicenseService licenseService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
    return licenseService.getLicenseByOrg(organizationId);
  }

  @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
  public License getLicences(
      @PathVariable("organizationId") String organizationId,
      @PathVariable("licenseId") String licenseId
  ) {
    return licenseService.getLicense(organizationId, licenseId);
  }
}
