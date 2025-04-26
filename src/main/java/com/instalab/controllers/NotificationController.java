package com.instalab.controllers;

import com.instalab.dtos.responses.NotificationResponse;
import com.instalab.entities.Notification;
import com.instalab.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/{professorId}")
    public ResponseEntity<NotificationResponse> send(
            @PathVariable UUID professorId,
            @RequestParam String message
    ) {
        Notification created = notificationService.sendNotification(professorId, message);
        NotificationResponse dto = new NotificationResponse(
                created.getId(),
                created.getMessage(),
                created.getSentAt(),
                created.isRead()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> list(@RequestParam UUID professorId) {
        List<NotificationResponse> list = notificationService
            .listForProfessor(professorId)
            .stream()
            .map(n -> new NotificationResponse(
                    n.getId(),
                    n.getMessage(),
                    n.getSentAt(),
                    n.isRead()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markRead(@PathVariable UUID notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }
}
