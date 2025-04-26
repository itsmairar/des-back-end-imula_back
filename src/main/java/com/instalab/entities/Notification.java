package com.instalab.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private OffsetDateTime sentAt;

    @Column(nullable = false)
    private boolean read;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private UserModel professor;

    public Notification() {}

    public Notification(UserModel professor, String message) {
        this.professor = professor;
        this.message = message;
        this.sentAt = OffsetDateTime.now();
        this.read = false;
    }

    public UUID getId() { return id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public OffsetDateTime getSentAt() { return sentAt; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public UserModel getProfessor() { return professor; }
    public void setProfessor(UserModel professor) { this.professor = professor; }
}
