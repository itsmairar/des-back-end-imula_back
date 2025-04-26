package com.instalab.configs;

import com.instalab.entities.Role;
import com.instalab.entities.UserModel;
import com.instalab.enums.RoleEnum;
import com.instalab.repositories.RoleRepository;
import com.instalab.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataLoader(RoleRepository roleRepo,
                      UserRepository userRepo,
                      PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder  = encoder;
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
                "UCSal"
            );
            admin.setRoles(Set.of(
                roleRepo.findByName(RoleEnum.ROLE_ADMIN).get()
            ));
            userRepo.save(admin);
        }
    }
}