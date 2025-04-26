package com.instalab.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "installation_solicitation_tb")
public class InstallationSolicitationModel {
    //Depende da criação do LaboratoryModel
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID installSolicitationId;
//    @ManyToMany(cascade = CascadeType.ALL)
//    private List<SoftwareModel> softwareList;
//    private LaboratoryModel laboratory;
//    private LocalDate dateUse;

    public InstallationSolicitationModel() {

    }



}
