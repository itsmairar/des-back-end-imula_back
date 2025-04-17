package com.instalab.dtos.responses.util;

public record LaboratorySimpleResponse(
        Long laboratoryId,
        String laboratoryName,
        Boolean laboratoryAvailability
) {}
