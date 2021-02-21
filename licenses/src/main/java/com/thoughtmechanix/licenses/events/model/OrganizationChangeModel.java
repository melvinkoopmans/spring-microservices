package com.thoughtmechanix.licenses.events.model;

public class OrganizationChangeModel {

  private String type;
  private String action;
  private String organizationId;
  private String correlationId;

  public OrganizationChangeModel() {
    super();
  }

  public OrganizationChangeModel(String type, String action, String organizationId, String correlationId) {
    super();
    this.type = type;
    this.action = action;
    this.organizationId = organizationId;
    this.correlationId = correlationId;
  }

  public String getType() {
    return type;
  }

  public String getAction() {
    return action;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public String getCorrelationId() {
    return correlationId;
  }
}
