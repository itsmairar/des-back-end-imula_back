package com.instalab.dtos.responses;


import com.instalab.dtos.responses.util.LaboratorySimpleResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.LicenseModel;
import com.instalab.entities.SoftwareModel;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record SoftwareResponse(UUID softwareId,
                               String softwareName,
                               String softwareDescription,
                               String softwareVersion,
                               String softwareAuthor,
                               String softwareLink,
                               LicenseModel licenseModel,
                               LocalDate requestDate,
                               Boolean availability,
                               Boolean softwareInstalled,
                               Set<LaboratorySimpleResponse> laboratoriesForInstallation) {

    public static SoftwareResponse parseToSoftwareResponse(SoftwareModel softwareModel) {
        return new SoftwareResponse(
                softwareModel.getSoftwareId(),
                softwareModel.getSoftwareName(),
                softwareModel.getSoftwareDescription(),
                softwareModel.getSoftwareVersion(),
                softwareModel.getSoftwareAuthor(),
                softwareModel.getSoftwareLink(),
                softwareModel.getLicenseModel(),
                softwareModel.getRegistrationDate(),
                softwareModel.isAvailable(),
                softwareModel.isInstalled(),
                softwareModel.getLaboratoriesList()
                        .stream()
                        .map(lab -> new LaboratorySimpleResponse(
                                lab.getLaboratoryId(),
                                lab.getLaboratoryName(),
                                lab.getLaboratoryAvailability()))
                        .collect(Collectors.toSet())
        );
    }

    public static SoftwareResponse parseToSoftwareResponse(SoftwareModel softwareModel, Set<LaboratoryModel> laboratories) {
        return new SoftwareResponse(
                softwareModel.getSoftwareId(),
                softwareModel.getSoftwareName(),
                softwareModel.getSoftwareDescription(),
                softwareModel.getSoftwareVersion(),
                softwareModel.getSoftwareAuthor(),
                softwareModel.getSoftwareLink(),
                softwareModel.getLicenseModel(),
                softwareModel.getRegistrationDate(),
                softwareModel.isAvailable(),
                softwareModel.isInstalled(),
                laboratories.stream()
                        .map(lab -> new LaboratorySimpleResponse(
                                lab.getLaboratoryId(),
                                lab.getLaboratoryName(),
                                lab.getLaboratoryAvailability()))
                        .collect(Collectors.toSet())
        );
    }

}
