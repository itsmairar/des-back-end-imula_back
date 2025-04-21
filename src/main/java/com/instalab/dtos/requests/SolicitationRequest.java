package com.instalab.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SolicitationModel;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public record SolicitationRequest(
        Set<UUID> softwaresSolicited,
        Long laboratoryId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate utilizationDate
) {
    public SolicitationModel toSolicitationModel(SolicitationRequest solicitationRequest, LaboratoryModel laboratory) {
        return new SolicitationModel(
                new LinkedHashSet<>(),
                laboratory.getLaboratoryId(),
                solicitationRequest.utilizationDate()
        );
    }
}
