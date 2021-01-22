package com.thoughtmechanix.licenses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {

  @Id
  @Column(name = "license_id", nullable = false)
  private String licenseId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "organization_id", nullable = false)
  private String organizationId;

  @Column(name = "comment")
  private String comment;

  public String getLicenseId() {
    return licenseId;
  }

  public License withId(String licenseId) {
    this.licenseId = licenseId;
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

  public String getComment() {
    return comment;
  }

  public License withComment(String comment) {
    this.comment = comment;
    return this;
  }
}
