package com.instalab.controllers;

import com.instalab.dtos.requests.SoftwareRequest;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.services.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    //Endpoint para cadastrar novo software
    //Perfil: Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<Void> registerSoftware(@RequestBody SoftwareRequest softwareRequest) {
        SoftwareResponse responseNewSoftware = softwareService.createSoftware(softwareRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseNewSoftware.softwareId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    //Endpoint para listar todos os softwares cadastrados
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @GetMapping()
    public ResponseEntity<List<SoftwareResponse>> allSoftwares(){
        List<SoftwareResponse> softwareResponseList = softwareService.getAllSoftware();
        return ResponseEntity.status(HttpStatus.OK).body(softwareResponseList);
    }

    //Endpoint para listar um software cadastrado baseado no ID
    //Perfil: Admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{softwareId}")
    public ResponseEntity<SoftwareResponse> findSoftwareById(@PathVariable UUID softwareId){
        SoftwareResponse softwareRegistred = softwareService.getSoftwareById(softwareId);
        return ResponseEntity.status(HttpStatus.OK).body(softwareRegistred);
    }

    //Endpoint para editar um software cadastrado baseado no ID
    //Perfil: Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{softwareId}")
    public ResponseEntity<Void> updateSoftware(@RequestBody SoftwareRequest softwareRequest, @PathVariable UUID softwareId) {
        softwareService.updateSoftware(softwareRequest, softwareId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
