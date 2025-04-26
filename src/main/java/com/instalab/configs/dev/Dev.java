package com.instalab.configs.dev;

import com.instalab.entities.LaboratoryModel;
import com.instalab.entities.SoftwareModel;
import com.instalab.entities.UserModel;
import com.instalab.enums.LicenseEnum;
import com.instalab.entities.LicenseModel;
import com.instalab.repositories.LaboratoryRepository;
import com.instalab.repositories.LicenseRepository;
import com.instalab.repositories.SoftwareRepository;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
@Profile("dev")
public class Dev {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner runner(){
        return args -> {

            UserModel usuario = new UserModel(
                    "Adminisnastror",
                    "admin@admin.com",
                    passwordEncoder.encode("admin"),
                    "Minha Empresa Ltda"
            );
            userRepository.save(usuario);

            LicenseModel l1 = new LicenseModel(1, LicenseEnum.FREE.name());
            LicenseModel l2 = new LicenseModel(2, LicenseEnum.PAID.name());
            licenseRepository.save(l1);
            licenseRepository.save(l2);

            LaboratoryModel lab1 = new LaboratoryModel("Laboratorio 1", true, new LinkedHashSet<>() );
//
            SoftwareModel soft1 = new SoftwareModel(
                    "ChatGPT Helper",
                    "Assistente inteligente baseado em IA",
                    "1.0.0",
                    "OpenAI",
                    "https://openai.com/chatgpt",
                    l1,
                    true);
            soft1.getLaboratoriesList().add(lab1);
            softwareRepository.save(soft1);
        };
    }
}
