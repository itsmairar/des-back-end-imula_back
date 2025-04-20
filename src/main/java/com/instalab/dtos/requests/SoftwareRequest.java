package com.instalab.dtos.requests;

import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.LicenseModel;
import com.instalab.entities.SoftwareModel;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public record SoftwareRequest(
                             String softwareName,
                             String softwareDescription,
                             String softwareVersion,
                             String softwareAuthor,
                             String softwareLink,
                             Integer licenseCode,
                             LocalDate requestDate,
                             Boolean availability
) {

    public SoftwareModel toSoftwareModel(SoftwareRequest softwareRequest, LicenseModel license) {
        return new SoftwareModel(
                softwareRequest.softwareName(),
                softwareRequest.softwareDescription(),
                softwareRequest.softwareVersion(),
                softwareRequest.softwareAuthor(),
                softwareRequest.softwareLink(),
                license,
                softwareRequest.requestDate(),
                softwareRequest.availability()
        );
    }


}
