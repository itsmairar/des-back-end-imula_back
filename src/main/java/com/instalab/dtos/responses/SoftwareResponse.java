package com.instalab.dtos.responses;

import com.instalab.models.LicenseModel;
import com.instalab.models.SoftwareModel;

import java.time.LocalDate;
import java.util.UUID;

public record SoftwareResponse(UUID softwareId,
                               String softwareName,
                               String softwareDescription,
                               String softwareVersion,
                               String softwareAuthor,
                               String softwareLink,
                               LicenseModel licenseModel,
                               LocalDate requestDate,
                               Boolean availability) {
    public static SoftwareResponse parseToSoftwareResponse(SoftwareModel softwareModel) {
        return new SoftwareResponse(
                softwareModel.getSoftwareId(),
                softwareModel.getSoftwareName(),
                softwareModel.getSoftwareDescription(),
                softwareModel.getSoftwareVersion(),
                softwareModel.getSoftwareAuthor(),
                softwareModel.getSoftwareLink(),
                softwareModel.getLicenseEnum(),
                softwareModel.getRequestDate(),
                softwareModel.getAvailability()
        );
    }
}
