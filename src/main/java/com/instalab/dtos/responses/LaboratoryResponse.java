package com.instalab.dtos.responses;

import com.instalab.dtos.responses.util.SoftwareSimpleResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;

import java.util.Set;
import java.util.stream.Collectors;

public record LaboratoryResponse(
        Long laboratoryId,
        String laboratoryName,
        Boolean laboratoryAvailability,
        Set<SoftwareSimpleResponse> softwaresInstalled) {
    public static LaboratoryResponse parseToLaboratoryResponse(LaboratoryModel laboratoryModel) {
        return new LaboratoryResponse(
                laboratoryModel.getLaboratoryId(),
                laboratoryModel.getLaboratoryName(),
                laboratoryModel.getLaboratoryAvailability(),
                laboratoryModel.getSoftwaresInstalled()
                        .stream()
                        .map(software -> new SoftwareSimpleResponse(
                                software.getSoftwareId(),
                                software.getSoftwareName(),
                                software.getSoftwareDescription(),
                                software.getSoftwareVersion(),
                                software.getSoftwareAuthor(),
                                software.getSoftwareLink(),
                                software.getLicenseModel(),
                                software.getRequestDate(),
                                software.isAvailable()))
                        .collect(Collectors.toSet())
        );
    }
    public static LaboratoryResponse parseToLaboratoryResponse(
            LaboratoryModel laboratoryModel,
            Set<SoftwareModel> softwares) {
        return new LaboratoryResponse(
                laboratoryModel.getLaboratoryId(),
                laboratoryModel.getLaboratoryName(),
                laboratoryModel.getLaboratoryAvailability(),
                softwares.stream()
                        .map(software -> new SoftwareSimpleResponse(
                                software.getSoftwareId(),
                                software.getSoftwareName(),
                                software.getSoftwareDescription(),
                                software.getSoftwareVersion(),
                                software.getSoftwareAuthor(),
                                software.getSoftwareLink(),
                                software.getLicenseModel(),
                                software.getRequestDate(),
                                software.isAvailable()))
                        .collect(Collectors.toSet())
        );
    }


}
