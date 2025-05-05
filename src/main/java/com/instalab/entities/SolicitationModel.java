package com.instalab.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="solicitation_tb")
public class SolicitationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solicitationId;

    @ManyToMany
    @JoinTable(
            name = "software_solicitation",
            joinColumns = @JoinColumn(name = "solicitation_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id")
    )
    private Set<SoftwareModel> softwaresSolicitedByUUID;

    @ManyToMany
    @JoinTable(
            name = "softwareNeedInstalation_solicitation",
            joinColumns = @JoinColumn(name = "solicitation_id"),
            inverseJoinColumns = @JoinColumn(name = "softwareNeedInstalation_id")
    )
    private Set<SoftwareModel> needInstalation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel professor;

    private Long laboratoryId;
    private LocalDate utilizationDate;
    private Boolean validated;
    private Boolean executed;

    public SolicitationModel() {}

    public SolicitationModel(
            Set<SoftwareModel> softwaresSolicitedByUUID,
            Long laboratoryId,
            LocalDate utilizationDate,
            UserModel professor
    ) {
        this.softwaresSolicitedByUUID = softwaresSolicitedByUUID;
        this.laboratoryId = laboratoryId;
        this.utilizationDate = utilizationDate;
        this.needInstalation = new HashSet<>();
        this.professor = professor;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public Set<SoftwareModel> getSoftwaresSolicitedByUUID() {
        return softwaresSolicitedByUUID;
    }

    public void setSoftwaresSolicitedByUUID(Set<SoftwareModel> softwaresSolicited) {
        this.softwaresSolicitedByUUID = softwaresSolicited;
    }

    public Long getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Long laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public LocalDate getUtilizationDate() {
        return utilizationDate;
    }

    public void setUtilizationDate(LocalDate utilizationDate) {
        this.utilizationDate = utilizationDate;
    }

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean verified) {
        this.validated = verified;
    }

    public Boolean isExecuted() {
        return executed;
    }

    public void setExecuted(Boolean completed) {
        this.executed = completed;
    }

    public Set<SoftwareModel> getNeedInstalation() {
        return needInstalation;
    }

    public void setNeedInstalation(Set<SoftwareModel> needInstalation) {
        this.needInstalation = needInstalation;
    }

    public UserModel getProfessor() {
        return professor;
    }

    public void setProfessor(UserModel professor) {
        this.professor = professor;
    }
}
