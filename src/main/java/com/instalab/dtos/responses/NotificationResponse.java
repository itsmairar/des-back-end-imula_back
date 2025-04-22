package com.instalab.dtos.responses;

import java.time.OffsetDateTime;
import java.util.UUID;

public record NotificationResponse(
    UUID id,
    String message,
    OffsetDateTime sentAt,
    boolean read
) {}
