package com.thoughtmechanix.licenses.model;

public class License {

  private String id;
  private String name;
  private String type;
  private String organizationId;

  public String getId() {
    return id;
  }

  public License withId(String licenseId) {
    this.id = licenseId;
    return this;
  }

  public String getName() {
    return name;
  }

  public License withProductName(String name) {
    this.name = name;
    return this;
  }

  public String getType() {
    return type;
  }

  public License withLicenseType(String type) {
    this.type = type;
    return this;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public License withOrganizationId(String organizationId) {
    this.organizationId = organizationId;
    return this;
  }
}
