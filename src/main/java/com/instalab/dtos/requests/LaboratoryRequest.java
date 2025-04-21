package com.instalab.dtos.requests;

import com.instalab.entities.LaboratoryModel;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public record LaboratoryRequest(
        @NotNull(message = "O nome do laboratório é obrigatório.")
        String laboratoryName,
        @NotNull(message = "A disponibilidade do laboratório é obrigatória.")
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
