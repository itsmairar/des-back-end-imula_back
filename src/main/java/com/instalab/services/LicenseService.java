package com.instalab.services;


import com.instalab.entities.LicenseModel;
import com.instalab.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;

    public LicenseModel getLicense(Integer codeEnum){
        LicenseModel licenseModel = licenseRepository.getReferenceById(codeEnum);
        return licenseModel;
    }



}
