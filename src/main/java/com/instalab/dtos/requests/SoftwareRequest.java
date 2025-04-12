package com.instalab.dtos.requests;

import com.instalab.models.LicenseModel;
import com.instalab.models.SoftwareModel;

import java.time.LocalDate;

public record SoftwareRequest(
                             String softwareName,
                             String softwareDescription,
                             String softwareVersion,
                             String softwareAuthor,
                             String softwareLink,
                             Integer licenseCode,
                             LocalDate requestDate) {

    public SoftwareModel toSoftwareModel(SoftwareRequest softwareRequest, LicenseModel license) {
        return new SoftwareModel(
                softwareRequest.softwareName,
                softwareRequest.softwareDescription,
                softwareRequest.softwareVersion,
                softwareRequest.softwareAuthor,
                softwareRequest.softwareLink,
                license,
                softwareRequest.requestDate);
    }


}
