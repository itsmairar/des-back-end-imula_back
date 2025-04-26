package com.instalab.services;

import com.instalab.entities.UserModel;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        UserModel user = userRepository
            .findByEmail(login)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuário não encontrado: " + login
            ));

        String[] authorities = user.getRoles().stream()
            .map(role -> role.getName().toString())
            .toArray(String[]::new);

        return User.builder()
                .username(login)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}