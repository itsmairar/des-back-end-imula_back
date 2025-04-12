package com.instalab.services;

import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.models.LicenseModel;
import com.instalab.models.SoftwareModel;
import com.instalab.repositories.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private LicenseService licenseService;


    public SoftwareResponse createSoftware(SoftwareRequest softwareRequest) {
        LicenseModel license = licenseService.getLicense(softwareRequest.licenseCode());
        SoftwareModel newSoftware = softwareRequest.toSoftwareModel(softwareRequest, license);
        softwareRepository.save(newSoftware);
        return SoftwareResponse.parseToSoftwareResponse(newSoftware);
    }


    public List<SoftwareResponse> getAllSoftware() {
        List<SoftwareModel> softwareList = softwareRepository.findAll();
        return softwareList.stream()
                .map(SoftwareResponse::parseToSoftwareResponse)
                .collect(Collectors.toList());
    }

    public SoftwareResponse getSoftwareById(UUID id) {
        SoftwareModel software = softwareRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Software not found"));
        return SoftwareResponse.parseToSoftwareResponse(software);
    }

    public SoftwareResponse updateSoftware(SoftwareRequest softwareRequest, UUID softwareId) {
        SoftwareModel softwareRegistred = softwareRepository.findBySoftwareId(softwareId);
        LicenseModel license = licenseService.getLicense(softwareRequest.licenseCode());
        buildChanges(softwareRegistred, softwareRequest, license);
        softwareRepository.save(softwareRegistred);
        return SoftwareResponse.parseToSoftwareResponse(softwareRegistred);
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
