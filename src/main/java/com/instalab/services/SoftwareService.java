package com.instalab.services;

import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.LicenseModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.repositories.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private LicenseService licenseService;

    @Lazy
    @Autowired
    private LaboratoryService laboratoryService;


    @Transactional
    public SoftwareResponse createSoftware(SoftwareRequest softwareRequest) {
        LicenseModel license = licenseService.getLicense(softwareRequest.licenseCode());
        SoftwareModel newSoftware = softwareRequest.toSoftwareModel(softwareRequest, license);
        newSoftware.setSoftwareInstalled(false);
        softwareRepository.save(newSoftware);
        return SoftwareResponse.parseToSoftwareResponse(newSoftware);
    }


    public List<SoftwareResponse> getAllSoftware() {
        List<SoftwareModel> softwareList = softwareRepository.findAll();
        return softwareList.stream()
                .map(SoftwareResponse::parseToSoftwareResponse)
                .collect(Collectors.toList());
    }

    public SoftwareResponse getSoftwareById(UUID softwareId) {
        SoftwareModel software = softwareRepository.findById(softwareId)
                .orElseThrow(() -> new NoSuchElementException("Software not found"));
        Set<LaboratoryModel> laboratoriesAssociates = laboratoryService.getLaboratoriesBySoftwareId(softwareId);
        return SoftwareResponse.parseToSoftwareResponse(software, laboratoriesAssociates);
    }

    public SoftwareModel findSoftwareBySoftwarId(UUID softwareId) {
        return softwareRepository.findBySoftwareId(softwareId);
    }

    @Transactional
    public void updateSoftware(SoftwareRequest softwareRequest, UUID softwareId) {
        SoftwareModel softwareRegistred = softwareRepository.findBySoftwareId(softwareId);
        LicenseModel license = licenseService.getLicense(softwareRequest.licenseCode());
        buildChanges(softwareRegistred, softwareRequest, license);
        softwareRepository.save(softwareRegistred);
    }

    private static SoftwareModel buildChanges(SoftwareModel softwareRegistred, SoftwareRequest softwareRequest, LicenseModel license) {
        softwareRegistred.setSoftwareName(softwareRequest.softwareName());
        softwareRegistred.setSoftwareDescription(softwareRequest.softwareDescription());
        softwareRegistred.setSoftwareVersion(softwareRequest.softwareVersion());
        softwareRegistred.setSoftwareAuthor(softwareRegistred.getSoftwareAuthor());
        softwareRegistred.setSoftwareLink(softwareRequest.softwareLink());
        softwareRegistred.setLicenseModel(license);
        softwareRegistred.setRequestDate(softwareRequest.requestDate());
        softwareRegistred.setSoftwareAvailability(softwareRequest.availability());
        return softwareRegistred;
    }

    //Metodo que retorna uma lista (Set) de todos os softwares instalados em um determinado laboratorio
    public Set<SoftwareModel> getSoftwaresByLaboratoryId(Long laboratoryId) {
        return softwareRepository.findByLaboratoriesList_LaboratoryId(laboratoryId);
    }

    public void disableSoftware(UUID softwareId) {
        SoftwareModel softwareRegistred = softwareRepository.findBySoftwareId(softwareId);
        softwareRegistred.setSoftwareAvailability(Boolean.FALSE);
        softwareRepository.save(softwareRegistred);
    }








}
