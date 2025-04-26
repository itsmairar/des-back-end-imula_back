package com.instalab.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="software_tb")
public class SoftwareModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID softwareId; //ID do software
    private String softwareName; //Nome do software
    private String softwareDescription; //Descricao do software
    private String softwareVersion; //Versao do software
    private String softwareAuthor; //Autor do software (pessoa ou empresa)
    private String softwareLink; //link para download
    @ManyToOne
    @JoinColumn(name = "license_id")
    private LicenseModel licenseModel; //licensa (gratuito - FREE / pago - PAID)
    private LocalDate registrationDate; //data da requisicao
    private Boolean softwareAvailability = true; //disponibilidade de instalacao
    private Boolean softwareInstalled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "software_laboratory",
            joinColumns = @JoinColumn(name = "softwareId"),
            inverseJoinColumns = @JoinColumn(name = "laboratoryId")
    )
    private Set<LaboratoryModel> laboratoriesList;

    public SoftwareModel() {
        laboratoriesList = new LinkedHashSet<>();
    }

    //Construtor usado apenas para testes
    public SoftwareModel(UUID softwareId,
                         String softwareName,
                         String softwareDescription,
                         String softwareVersion,
                         String softwareAuthor,
                         String softwareLink,
                         LicenseModel licenseModel) {
        this.softwareId = softwareId;
        this.softwareName = softwareName;
        this.softwareDescription = softwareDescription;
        this.softwareVersion = softwareVersion;
        this.softwareAuthor = softwareAuthor;
        this.softwareLink = softwareLink;
        this.licenseModel = licenseModel;
        this.registrationDate = LocalDate.now();
    }

    public SoftwareModel(
            String softwareName,
            String softwareDescription,
            String softwareVersion,
            String softwareAuthor,
            String softwareLink,
            LicenseModel licenseModel,
            Boolean softwareAvailability
    ) {
        this.softwareName = softwareName;
        this.softwareDescription = softwareDescription;
        this.softwareVersion = softwareVersion;
        this.softwareAuthor = softwareAuthor;
        this.softwareLink = softwareLink;
        this.licenseModel = licenseModel;
        this.registrationDate = LocalDate.now();
        this.softwareAvailability = softwareAvailability;
        laboratoriesList = new LinkedHashSet<>();


    }
    public UUID getSoftwareId() {
        return softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSoftwareDescription() {
        return softwareDescription;
    }

    public void setSoftwareDescription(String softwareDescription) {
        this.softwareDescription = softwareDescription;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSoftwareAuthor() {
        return softwareAuthor;
    }

    public void setSoftwareAuthor(String softwareAuthor) {
        this.softwareAuthor = softwareAuthor;
    }

    public String getSoftwareLink() {
        return softwareLink;
    }

    public void setSoftwareLink(String softwareLink) {
        this.softwareLink = softwareLink;
    }

    public LicenseModel getLicenseModel() {
        return licenseModel;
    }

    public void setLicenseModel(LicenseModel licenseModel) {
        this.licenseModel = licenseModel;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate requestDate) {
        this.registrationDate = requestDate;
    }

    public Boolean isAvailable() {
        return softwareAvailability;
    }

    public void setSoftwareAvailability(Boolean availability) {
        this.softwareAvailability = availability;
    }

    public Boolean isInstalled() {
        return softwareInstalled;
    }

    public void setSoftwareInstalled(Boolean softwareInstalled) {
        this.softwareInstalled = softwareInstalled;
    }

    public Set<LaboratoryModel> getLaboratoriesList() {
        return laboratoriesList;
    }

    public void setLaboratoriesList(Set<LaboratoryModel> laboratoriesList) {
        this.laboratoriesList = laboratoriesList;
    }
}
