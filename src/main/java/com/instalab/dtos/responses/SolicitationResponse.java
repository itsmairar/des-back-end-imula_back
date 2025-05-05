package com.instalab.dtos.responses;

import com.instalab.dtos.responses.util.SoftwareSimpleResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.entities.SolicitationModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record SolicitationResponse (
        Long solicitationId,
        Set<SoftwareSimpleResponse> softwaresSolicited,
        Set<SoftwareSimpleResponse> needInstalation,
        Long laboratory,
        LocalDate utilizationDate,
        Boolean verified,
        Boolean completed,
        String professorName
) {
    public static SolicitationResponse parseToSolicitationResponse(SolicitationModel solicitationModel,
                                                                    LaboratoryModel laboratory,
                                                                    List<SoftwareModel> needInstalation) {
        return new SolicitationResponse(
                solicitationModel.getSolicitationId(),
                solicitationModel.getSoftwaresSolicitedByUUID().stream()
                        .map(software -> new SoftwareSimpleResponse(
                                software.getSoftwareId(),
                                software.getSoftwareName(),
                                software.getSoftwareDescription(),
                                software.getSoftwareVersion(),
                                software.getSoftwareAuthor(),
                                software.getSoftwareLink(),
                                software.getLicenseModel(),
                                software.getRegistrationDate(),
                                software.isAvailable()
                        ))
                        .collect(Collectors.toSet()),
                needInstalation.stream()
                        .map(software -> new SoftwareSimpleResponse(
                                software.getSoftwareId(),
                                software.getSoftwareName(),
                                software.getSoftwareDescription(),
                                software.getSoftwareVersion(),
                                software.getSoftwareAuthor(),
                                software.getSoftwareLink(),
                                software.getLicenseModel(),
                                software.getRegistrationDate(),
                                software.isAvailable()
                        ))
                        .collect(Collectors.toSet()),
                laboratory.getLaboratoryId(),
                solicitationModel.getUtilizationDate(),
                solicitationModel.isValidated(),
                solicitationModel.isExecuted(),
                solicitationModel.getProfessor().getFullname()
        );
    }

    public static SolicitationResponse parseToSolicitationResponse(SolicitationModel solicitationModel) {
        return new SolicitationResponse(
                solicitationModel.getSolicitationId(),
                solicitationModel.getSoftwaresSolicitedByUUID().stream()
                        .map(software -> new SoftwareSimpleResponse(
                                software.getSoftwareId(),
                                software.getSoftwareName(),
                                software.getSoftwareDescription(),
                                software.getSoftwareVersion(),
                                software.getSoftwareAuthor(),
                                software.getSoftwareLink(),
                                software.getLicenseModel(),
                                software.getRegistrationDate(),
                                software.isAvailable()
                        ))
                        .collect(Collectors.toSet()),
                solicitationModel.getNeedInstalation().stream()
                        .map(software -> new SoftwareSimpleResponse(
                                software.getSoftwareId(),
                                software.getSoftwareName(),
                                software.getSoftwareDescription(),
                                software.getSoftwareVersion(),
                                software.getSoftwareAuthor(),
                                software.getSoftwareLink(),
                                software.getLicenseModel(),
                                software.getRegistrationDate(),
                                software.isAvailable()
                        ))
                        .collect(Collectors.toSet()),
                solicitationModel.getLaboratoryId(),
                solicitationModel.getUtilizationDate(),
                solicitationModel.isValidated(),
                solicitationModel.isExecuted(),
                solicitationModel.getProfessor().getFullname()
        );
    }
}
