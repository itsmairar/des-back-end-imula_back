package com.instalab.repositories;

import com.instalab.entities.LaboratoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends JpaRepository<LaboratoryModel, Long> {
}
