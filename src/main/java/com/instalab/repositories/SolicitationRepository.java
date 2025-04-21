package com.instalab.repositories;

import com.instalab.entities.SolicitationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitationRepository extends JpaRepository<SolicitationModel, Long> {
}
