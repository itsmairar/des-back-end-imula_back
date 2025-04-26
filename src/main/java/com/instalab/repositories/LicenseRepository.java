package com.instalab.repositories;

import com.instalab.entities.LicenseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<LicenseModel, Integer> {
}
