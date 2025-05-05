package com.instalab.controllers;

import com.instalab.dtos.requests.SolicitationRequest;
import com.instalab.dtos.responses.SolicitationResponse;
import com.instalab.services.SolicitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/solicitation")
public class SolicitationController {

    @Autowired
    private SolicitationService solicitationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'PROFESSOR')")
    public ResponseEntity<List<SolicitationResponse>> getAllSolicitations() {
        List<SolicitationResponse> solicitations = solicitationService.getAllSolicitations();
        return ResponseEntity.status(HttpStatus.OK).body(solicitations);

    }

    @PostMapping("/new")
    public ResponseEntity<SolicitationResponse> sendSolicitation(@RequestBody SolicitationRequest solicitation) {
        SolicitationResponse newSolicitation = solicitationService.createSolicitation(solicitation);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSolicitation.solicitationId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/execute/{solicitationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> executeSolicitation(@PathVariable Long solicitationId) {
        solicitationService.executeSolicitation(solicitationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/edit/{solicitationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> editSolicitation(@PathVariable Long solicitationId,
                                                   @RequestBody Long laboratoryId) {
        solicitationService.editSolicitation(solicitationId, laboratoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
