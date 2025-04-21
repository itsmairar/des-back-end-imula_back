package com.instalab.services;

import com.instalab.dtos.requests.SolicitationRequest;
import com.instalab.dtos.responses.SolicitationResponse;
import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.entities.SolicitationModel;
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

    @Transactional
    public SolicitationResponse createSolicitation(SolicitationRequest solicitationRequest) {

        //Precisa adicionar a logica de envio da notificacao para o professor informando sobre a validação de sua solicitacao

        LaboratoryModel laboratory = laboratoryService.findLaboratoryById(solicitationRequest.laboratoryId());
        Set<UUID> installedSoftwareIds = getSoftwaresUUIDsInstalled(laboratory);

        //Lista de softwares solicitados que ainda nao foram instalados no laboratorio
        Set<UUID> notInstalled = validateSoftwares(installedSoftwareIds, solicitationRequest);

        if (notInstalled.isEmpty()) {
            throw new IllegalArgumentException(
                    "Todos os softwares solicitados já estão instalados no laboratório selecionado."
            );
        }

        laboratory.setLaboratoryAvailability(Boolean.FALSE);

        SolicitationModel newSolicitation = solicitationRequest.toSolicitationModel(solicitationRequest, laboratory);
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
        return solicitations.stream()
                .map(SolicitationResponse::parseToSolicitationResponse)
                .collect(Collectors.toList());
    }

    public void executeSolicitation(Long SolicitationId) {
        //Precisa adicionar a logica de envio da notificacao para o professor informando sobre a conclusao de sua solicitacao
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
        //Softwares instalados no novo laboratorio selecionado
        Set<UUID> installedSoftwareIds = getSoftwaresUUIDsInstalled(laboratory);

        //Softwares que foram solicitados na criação da solicitação
        Set<UUID> solicitedSoftwareIds = solicitation.getSoftwaresSolicitedByUUID()
                .stream()
                .map(SoftwareModel::getSoftwareId)
                .collect(Collectors.toSet());

        //Filtro dos softwares solicitados que ainda não estão instalados no novo laboratorio selecionado
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
