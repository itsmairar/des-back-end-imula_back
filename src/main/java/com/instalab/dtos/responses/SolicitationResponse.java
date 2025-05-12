package com.instalab.dtos.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.instalab.dtos.responses.util.SoftwareSimpleResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.entities.SolicitationModel;

public record SolicitationResponse(
                Long solicitationId,
                Set<SoftwareSimpleResponse> softwaresSolicited,
                Set<SoftwareSimpleResponse> needInstalation,
                String laboratory,
                LocalDate utilizationDate,
                LocalDate solicitationDate,
                Boolean verified,
                Boolean executed,
                String professorName) {
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
                                                                software.isAvailable()))
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
                                                                software.isAvailable()))
                                                .collect(Collectors.toSet()),
                                laboratory.getLaboratoryName(),
                                solicitationModel.getUtilizationDate(),
                                solicitationModel.getSolicitationDate(),
                                solicitationModel.isValidated(),
                                solicitationModel.isExecuted(),
                                solicitationModel.getProfessor().getFullname());
        }

}
