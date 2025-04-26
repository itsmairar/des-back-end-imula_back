package com.instalab.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "laboratory_tb")
public class LaboratoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long laboratoryId;
    private String laboratoryName;
    private Boolean laboratoryAvailability;

    @ManyToMany(mappedBy = "laboratoriesList")
    @JsonIgnore
    private Set<SoftwareModel> softwaresInstalled;

    public LaboratoryModel() {
        softwaresInstalled = new LinkedHashSet<>();
    }

    public LaboratoryModel(String laboratoryName, Boolean laboratoryAvailability, Set<SoftwareModel> softwaresInstalled) {
        this.laboratoryName = laboratoryName;
        this.laboratoryAvailability = laboratoryAvailability;
        this.softwaresInstalled = softwaresInstalled;
    }

    public Long getLaboratoryId() {
        return laboratoryId;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public Boolean getLaboratoryAvailability() {
        return laboratoryAvailability;
    }

    public void setLaboratoryAvailability(Boolean laboratoryAvailability) {
        this.laboratoryAvailability = laboratoryAvailability;
    }

    public Set<SoftwareModel> getSoftwaresInstalled() {
        return softwaresInstalled;
    }

    public void setSoftwaresInstalled(Set<SoftwareModel> listSoftware) {
        this.softwaresInstalled = listSoftware;
    }
}
