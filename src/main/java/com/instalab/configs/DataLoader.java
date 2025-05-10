package com.instalab.configs;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.instalab.entities.Role;
import com.instalab.entities.SoftwareModel;
import com.instalab.entities.SolicitationModel;
import com.instalab.entities.UserModel;
import com.instalab.enums.RoleEnum;
import com.instalab.repositories.RoleRepository;
import com.instalab.repositories.SolicitationRepository;
import com.instalab.repositories.UserRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final SolicitationRepository solictRepo;

    public DataLoader(RoleRepository roleRepo,
                      UserRepository userRepo,
                      PasswordEncoder encoder,
                      SolicitationRepository solictRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.solictRepo = solictRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // cria roles se não existirem
        for (RoleEnum r : RoleEnum.values()) {
            if (roleRepo.findByName(r).isEmpty()) {
                roleRepo.save(new Role(r));
            }
        }

        if (userRepo.findByEmail("admin@ucsal.com").isEmpty()) {
            UserModel admin = new UserModel(
                    "Administrador",
                    "admin@ucsal.com",
                    encoder.encode("admin123"),
                    "UCSal");
            admin.setRoles(Set.of(
                    roleRepo.findByName(RoleEnum.ROLE_ADMIN).get()));
            userRepo.save(admin);


            SolicitationModel s1 = new SolicitationModel(
            new HashSet<SoftwareModel>(),
            1L,
            LocalDate.now(),
            admin
            );
            s1.setExecuted(false);
            s1.setValidated(false);
            solictRepo.save(s1);
            
        }

     
        
        




        
    }
}