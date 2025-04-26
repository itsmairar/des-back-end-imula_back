package com.instalab.dtos.responses.util;

import com.instalab.entities.LicenseModel;

import java.time.LocalDate;
import java.util.UUID;

public record SoftwareSimpleResponse(
        UUID softwareId,
        String softwareName,
        String softwareDescription,
        String softwareVersion,
        String softwareAuthor,
        String softwareLink,
        LicenseModel licenseModel,
        LocalDate requestDate,
        Boolean availability
) {
}
