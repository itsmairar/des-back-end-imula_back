package com.instalab.services;

import com.instalab.dtos.requests.LaboratoryRequest;
import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.LaboratoryResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.repositories.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<LaboratoryResponse> getAllLaboratories() {
        List<LaboratoryModel> listLaboratories = laboratoryRepository.findAll();
        return listLaboratories.stream()
                .map(LaboratoryResponse::parseToLaboratoryResponse)
                .collect(Collectors.toList());
    }

//    Metodo para uso interno
    public List<LaboratoryModel> getAllLaboratoryModels(){
        return laboratoryRepository.findAll();
    }

    @Transactional
    public LaboratoryResponse createLaboratory(LaboratoryRequest laboratoryRequest) {
        LaboratoryModel newLaboratory = new LaboratoryModel(
                laboratoryRequest.laboratoryName(),
                laboratoryRequest.laboratoryAvailability(),
                new LinkedHashSet<>()
                );
        laboratoryRepository.save(newLaboratory);
        return LaboratoryResponse.parseToLaboratoryResponse(newLaboratory);
    }


}
