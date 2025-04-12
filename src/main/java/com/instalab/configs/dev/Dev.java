package com.instalab.configs.dev;

import com.instalab.enums.LicenseEnum;
import com.instalab.models.LicenseModel;
import com.instalab.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class Dev {

    @Autowired
    private LicenseRepository licenseRepository;

    @Bean
    public CommandLineRunner runner(){
        return args -> {
            LicenseModel l1 = new LicenseModel(1, LicenseEnum.FREE.name());
            LicenseModel l2 = new LicenseModel(2, LicenseEnum.PAID.name());
            licenseRepository.save(l1);
            licenseRepository.save(l2);
        };
    }
}
