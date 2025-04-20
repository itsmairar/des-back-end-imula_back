package com.instalab.repositories;

import com.instalab.entities.LaboratoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface LaboratoryRepository extends JpaRepository<LaboratoryModel, Long> {

    Set<LaboratoryModel> findBySoftwaresInstalled_SoftwareId(UUID softwareId);

}
