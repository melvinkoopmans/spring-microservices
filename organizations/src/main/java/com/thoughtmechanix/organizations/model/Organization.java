package com.thoughtmechanix.organizations.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "organizations")
public class Organization {

  @Id
  @Column(name = "organization_id")
  private String organizationId;

  @Column(name = "name")
  private String name;

  @Column(name = "contact_name")
  private String contactName;

  @Column(name = "contact_email")
  private String contactEmail;

  @Column(name = "contact_phone")
  private String contactPhone;

  public Organization setOrganizationId(String organizationId) {
    this.organizationId = organizationId;

    return this;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public String getName() {
    return name;
  }

  public String getContactName() {
    return contactName;
  }

  public Organization setContactName(String contactName) {
    this.contactName = contactName;

    return this;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public String getContactPhone() {
    return contactPhone;
  }
}
