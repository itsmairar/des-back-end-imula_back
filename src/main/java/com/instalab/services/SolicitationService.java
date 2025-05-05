package com.instalab.services;

import com.instalab.dtos.requests.SolicitationRequest;
import com.instalab.dtos.responses.SolicitationResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.entities.SolicitationModel;
import com.instalab.entities.UserModel;
import com.instalab.repositories.SolicitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SolicitationService {

    @Autowired
    private SolicitationRepository solicitationRepository;

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Transactional
    public SolicitationResponse createSolicitation(SolicitationRequest solicitationRequest) {

        LaboratoryModel laboratory = laboratoryService.findLaboratoryById(solicitationRequest.laboratoryId());
        Set<UUID> installedSoftwareIds = getSoftwaresUUIDsInstalled(laboratory);

        Set<UUID> notInstalled = validateSoftwares(installedSoftwareIds, solicitationRequest);

        if (notInstalled.isEmpty()) {
            throw new IllegalArgumentException(
                    "Todos os softwares solicitados já estão instalados no laboratório selecionado."
            );
        }

        laboratory.setLaboratoryAvailability(Boolean.FALSE);

        UserModel professor = authenticatedUserService.getAuthenticatedUser();

        SolicitationModel newSolicitation = solicitationRequest.toSolicitationModel(solicitationRequest, laboratory, professor);
        newSolicitation.setValidated(Boolean.TRUE);
        newSolicitation.setExecuted(Boolean.FALSE);

        List<SoftwareModel> softwaresModelsSolicited = softwareService.findAllSoftwaresByListIds(solicitationRequest.softwaresSolicited());
        softwaresModelsSolicited.forEach(software -> newSolicitation.getSoftwaresSolicitedByUUID().add(software));

        List<SoftwareModel> softwaresModelsNeedInstalation = softwareService.findAllSoftwaresByListIds(notInstalled);
        softwaresModelsNeedInstalation.forEach(software -> newSolicitation.getNeedInstalation().add(software));

        solicitationRepository.save(newSolicitation);
        return SolicitationResponse.parseToSolicitationResponse(newSolicitation, laboratory, softwaresModelsNeedInstalation);
    }

    public Set<UUID> getSoftwaresUUIDsInstalled(LaboratoryModel laboratory) {
        return laboratory.getSoftwaresInstalled()
                .stream()
                .map(SoftwareModel::getSoftwareId)
                .collect(Collectors.toSet());
    }

    private static Set<UUID> validateSoftwares(
            Set<UUID> installedSoftwareIds,
            SolicitationRequest softwaresRequested
    ) {
        return softwaresRequested.softwaresSolicited()
                .stream()
                .filter(softwareId -> !installedSoftwareIds.contains(softwareId))
                .collect(Collectors.toSet());
    }

    public List<SolicitationResponse> getAllSolicitations() {
        List<SolicitationModel> solicitations = solicitationRepository.findAll();

        return solicitations.stream().map(solicitation -> {
            LaboratoryModel lab = laboratoryService.findLaboratoryById(solicitation.getLaboratoryId());
            List<SoftwareModel> softwaresNeedInstallation = new ArrayList<>(solicitation.getNeedInstalation());

            return SolicitationResponse.parseToSolicitationResponse(
                    solicitation,
                    lab,
                    softwaresNeedInstallation
            );
        }).collect(Collectors.toList());
    }

    public void executeSolicitation(Long SolicitationId) {
        SolicitationModel solicitation = solicitationRepository.findById(SolicitationId).get();

        LaboratoryModel laboratory = laboratoryService.findLaboratoryById(solicitation.getLaboratoryId());
        laboratory.setLaboratoryAvailability(Boolean.TRUE);

        solicitation.setExecuted(Boolean.TRUE);
        solicitationRepository.save(solicitation);
    }

    public void editSolicitation(Long SolicitationId, Long laboratoryId) {
        SolicitationModel solicitation = solicitationRepository.findById(SolicitationId).get();
        solicitation.setLaboratoryId(laboratoryId);

        LaboratoryModel laboratory = laboratoryService.findLaboratoryById(solicitation.getLaboratoryId());
        Set<UUID> installedSoftwareIds = getSoftwaresUUIDsInstalled(laboratory);

        Set<UUID> solicitedSoftwareIds = solicitation.getSoftwaresSolicitedByUUID()
                .stream()
                .map(SoftwareModel::getSoftwareId)
                .collect(Collectors.toSet());

        Set<UUID> notInstalled = solicitedSoftwareIds
                .stream()
                .filter(softwareId -> !installedSoftwareIds.contains(softwareId))
                .collect(Collectors.toSet());

        if (notInstalled.isEmpty()) {
            throw new IllegalArgumentException(
                    "Todos os softwares solicitados já estão instalados no laboratório selecionado."
            );
        }

        List<SoftwareModel> softwaresNeedInstallation = softwareService.findAllSoftwaresByListIds(notInstalled);
        solicitation.setNeedInstalation(new HashSet<>(softwaresNeedInstallation));
        solicitation.setValidated(Boolean.TRUE);
        solicitation.setExecuted(Boolean.FALSE);

        solicitationRepository.save(solicitation);
    }
}
