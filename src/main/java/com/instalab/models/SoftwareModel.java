package com.instalab.models;

import jakarta.persistence.*;

import java.time.LocalDate;
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
    private LocalDate requestDate; //data da requisicao
    private Boolean availability; //disponibilidade de instalacao

    public LicenseModel getLicenseModel() {
        return licenseModel;
    }

    public void setLicenseModel(LicenseModel licenseModel) {
        this.licenseModel = licenseModel;
    }

    public SoftwareModel() {}

    //Construtor usado apenas para testes
    public SoftwareModel(UUID softwareId,
                         String softwareName,
                         String softwareDescription,
                         String softwareVersion,
                         String softwareAuthor,
                         String softwareLink,
                         LicenseModel licenseModel,
                         LocalDate requestDate) {
        this.softwareId = softwareId;
        this.softwareName = softwareName;
        this.softwareDescription = softwareDescription;
        this.softwareVersion = softwareVersion;
        this.softwareAuthor = softwareAuthor;
        this.softwareLink = softwareLink;
        this.licenseModel = licenseModel;
        this.requestDate = requestDate;
    }

    public SoftwareModel(
                    String softwareName,
                    String softwareDescription,
                    String softwareVersion,
                    String softwareAuthor,
                    String softwareLink,
                    LicenseModel licenseModel,
                    LocalDate requestDate) {
        this.softwareName = softwareName;
        this.softwareDescription = softwareDescription;
        this.softwareVersion = softwareVersion;
        this.softwareAuthor = softwareAuthor;
        this.softwareLink = softwareLink;
        this.licenseModel = licenseModel;
        this.requestDate = requestDate;
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

    public LicenseModel getLicenseEnum() {
        return licenseModel;
    }


    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
}
