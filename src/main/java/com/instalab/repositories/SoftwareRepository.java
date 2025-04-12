package com.instalab.repositories;

import com.instalab.models.SoftwareModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SoftwareRepository extends JpaRepository<SoftwareModel, UUID> {

    public SoftwareModel findBySoftwareId(UUID softwareId);
}
