package com.thoughtmechanix.licenses.model;

import java.io.Serializable;

public class Organization implements Serializable {

  private String organizationId;
  private String name;
  private String contactName;
  private String contactEmail;
  private String contactPhone;

  public String getOrganizationId() {
    return organizationId;
  }

  public Organization setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  public String getName() {
    return name;
  }

  public Organization setName(String name) {
    this.name = name;
    return this;
  }

  public String getContactName() {
    return contactName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public String getContactPhone() {
    return contactPhone;
  }
}
