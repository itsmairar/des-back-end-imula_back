package com.instalab.services;

import com.instalab.dtos.requests.LaboratoryRequest;
import com.instalab.dtos.responses.LaboratoryResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.repositories.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private SoftwareService softwareService;


    public List<LaboratoryResponse> getAllLaboratories() {
        List<LaboratoryModel> listLaboratories = laboratoryRepository.findAll();
        return listLaboratories.stream()
                .map(LaboratoryResponse::parseToLaboratoryResponse)
                .collect(Collectors.toList());
    }

    public LaboratoryResponse getLaboratoryById(Long laboratoryId) {
        LaboratoryModel laboratoryRegistred = laboratoryRepository.findById(laboratoryId).get();
        Set<SoftwareModel> softwaresAssociates = softwareService.getSoftwaresByLaboratoryId(laboratoryId);
        return LaboratoryResponse.parseToLaboratoryResponse(laboratoryRegistred, softwaresAssociates);
    }

    public LaboratoryModel findLaboratoryById(Long laboratoryId) {
        return laboratoryRepository.findById(laboratoryId).get();
    }

    //Metodo que retorna uma lista (Set) de todos os laboratorios que possuem um determinado software instalado
    public Set<LaboratoryModel> getLaboratoriesBySoftwareId(UUID softwareId) {
        return laboratoryRepository.findBySoftwaresInstalled_SoftwareId(softwareId);
    }

    @Transactional
    public LaboratoryResponse createLaboratory(LaboratoryRequest laboratoryRequest) {

        LaboratoryModel newLaboratory = laboratoryRequest.toLaboratoryModel(laboratoryRequest);
        laboratoryRepository.save(newLaboratory);
        return LaboratoryResponse.parseToLaboratoryResponse(newLaboratory);
    }

    public void updateLaboratory(Long laboratoryId, LaboratoryRequest laboratoryRequest) {
        LaboratoryModel laboratoryRegistred = laboratoryRepository.findById(laboratoryId).get();
        processUpdate(laboratoryRequest, laboratoryRegistred);

        Set<SoftwareModel> softwares = laboratoryRequest.softwaresInstalled()
                .stream()
                .map(softwareId -> {
                    SoftwareModel software = softwareService.findSoftwareBySoftwarId(softwareId);
                    software.getLaboratoriesList().add(laboratoryRegistred);
                    software.setSoftwareInstalled(true);
                    return software;
                })
                .collect(Collectors.toSet());
        laboratoryRegistred.getSoftwaresInstalled().addAll(softwares);
        laboratoryRepository.save(laboratoryRegistred);
    }

    private static LaboratoryModel processUpdate(LaboratoryRequest laboratoryRequest, LaboratoryModel laboratoryRegistred){
        laboratoryRegistred.setLaboratoryName(laboratoryRequest.laboratoryName());
        laboratoryRegistred.setLaboratoryAvailability(laboratoryRequest.laboratoryAvailability());
        return laboratoryRegistred;
    }

    @Transactional
    public void removeSoftware(Long laboratoryId, UUID softwareId) {
        LaboratoryModel laboratoryRegistred = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new NoSuchElementException("Laboratório não encontrado"));
        SoftwareModel software = softwareService.findSoftwareBySoftwarId(softwareId);

        laboratoryRegistred.getSoftwaresInstalled().remove(software);
        software.getLaboratoriesList().remove(laboratoryRegistred);
    }






}
