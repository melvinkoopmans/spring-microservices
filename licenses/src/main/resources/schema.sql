DROP TABLE IF EXISTS licenses;

CREATE TABLE licenses (
      license_id        VARCHAR(100) PRIMARY KEY NOT NULL,
      organization_id   TEXT NOT NULL,
      type      TEXT NOT NULL,
      name      TEXT NOT NULL,
      comment           VARCHAR(100)
);

INSERT INTO licenses (license_id,  organization_id, type, name)
VALUES ('f3831f8c-c338-4ebe-a82a-e2fc1d1ff78a', 'e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'user','customer-crm-co');