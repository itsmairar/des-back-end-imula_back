package com.instalab.dtos.requests;

//import com.instalab.entities.LaboratoryModel;
//
//import java.util.LinkedHashSet;


public record LaboratoryRequest(
        String laboratoryName,
        Boolean laboratoryAvailability
) {
//    public LaboratoryModel toLaboratoryModel(LaboratoryRequest laboratoryRequest) {
//        return new LaboratoryModel(
//                laboratoryRequest.laboratoryName(),
//                laboratoryRequest.laboratoryAvailability(),
//                new LinkedHashSet<>()
//        );
//    }
}
