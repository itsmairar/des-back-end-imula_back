package com.instalab.dtos.requests;

import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public record LaboratoryRequest(
        String laboratoryName,
        Boolean laboratoryAvailability,
        Set<UUID> softwaresInstalled
) {
    public LaboratoryModel toLaboratoryModel(LaboratoryRequest laboratoryRequest) {
        return new LaboratoryModel(
                laboratoryRequest.laboratoryName(),
                laboratoryRequest.laboratoryAvailability(),
                new LinkedHashSet<>()
        );
    }
}
