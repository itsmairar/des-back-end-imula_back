package com.instalab.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication app) {
        Dotenv dotenv = Dotenv.configure()
                              .directory("./")
                              .ignoreIfMalformed()
                              .ignoreIfMissing()
                              .load();

        dotenv.entries().forEach(e ->
            env.getSystemProperties().put(e.getKey(), e.getValue())
        );
    }
}
