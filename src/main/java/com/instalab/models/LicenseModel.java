package com.instalab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="license_tb")
public class LicenseModel {

    @Id

    private Integer licenseId;
    private String licenseName;

    public LicenseModel() {}

    public LicenseModel(Integer licenseId, String licenseName) {
        this.licenseId = licenseId;
        this.licenseName = licenseName;
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

}
