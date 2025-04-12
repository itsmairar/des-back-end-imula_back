package com.instalab.repositories;

import com.instalab.models.LicenseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<LicenseModel, Integer> {
}
