package com.instalab.services;

import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.LicenseModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.repositories.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LaboratoryService laboratoryService;


    @Transactional
    public SoftwareResponse createSoftware(SoftwareRequest softwareRequest) {
        LicenseModel license = licenseService.getLicense(softwareRequest.licenseCode());
        Set<LaboratoryModel> laboratoryList = new LinkedHashSet<>(laboratoryService.getAllLaboratoryModels());
        SoftwareModel newSoftware = softwareRequest.toSoftwareModel(softwareRequest, license);
        newSoftware.setLaboratoriesList(laboratoryList);
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
        SoftwareModel software = softwareRepository.findById(softwareId).orElseThrow(() -> new NoSuchElementException("Software not found"));
        return SoftwareResponse.parseToSoftwareResponse(software);
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
        return softwareRegistred;
    }









}
