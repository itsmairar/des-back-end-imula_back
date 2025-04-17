package com.instalab.controllers;

import com.instalab.dtos.requests.LaboratoryRequest;
import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.LaboratoryResponse;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.services.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping()
    public ResponseEntity<List<LaboratoryResponse>> getAllLaboratory() {
        List<LaboratoryResponse> laboratoriesListResponse = laboratoryService.getAllLaboratories();
        return ResponseEntity.status(HttpStatus.OK).body(laboratoriesListResponse);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> registerLaboratory(@RequestBody LaboratoryRequest laboratoryRequest) {
        LaboratoryResponse responseNewSoftware = laboratoryService.createLaboratory(laboratoryRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseNewSoftware.laboratoryId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
