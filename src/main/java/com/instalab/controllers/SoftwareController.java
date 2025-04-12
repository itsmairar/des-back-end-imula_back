package com.instalab.controllers;

import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.services.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    //Endpoint para cadastrar novo usuário
    @PostMapping("/new")
    public ResponseEntity<Void> registredSoftware(@RequestBody SoftwareRequest softwareRequest) {
        SoftwareResponse responseNewSoftware = softwareService.createSoftware(softwareRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseNewSoftware.softwareId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    //Endpoint para listar todos os softwares cadastrados
    @GetMapping()
    public ResponseEntity<List<SoftwareResponse>> allSoftwares(){
        List<SoftwareResponse> softwareResponseList = softwareService.getAllSoftware();
        return ResponseEntity.status(HttpStatus.OK).body(softwareResponseList);
    }

    //Endpoint para listar um software cadastrado baseado no ID
    @GetMapping({"/softwareId"})
    public ResponseEntity<SoftwareResponse> getSoftwareById(@PathVariable UUID softwareId){
        SoftwareResponse softwareRegistred = softwareService.getSoftwareById(softwareId);
        return ResponseEntity.status(HttpStatus.OK).body(softwareRegistred);
    }

}
