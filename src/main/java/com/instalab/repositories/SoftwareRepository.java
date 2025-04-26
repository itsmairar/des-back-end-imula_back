package com.instalab.repositories;

import com.instalab.entities.SoftwareModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface SoftwareRepository extends JpaRepository<SoftwareModel, UUID> {

    SoftwareModel findBySoftwareId(UUID softwareId);

    Set<SoftwareModel> findByLaboratoriesList_LaboratoryId(Long laboratoryId);
}
