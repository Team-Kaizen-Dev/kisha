package com.teamkaizen.kisha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executor;

@SpringBootApplication
public class KishaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KishaApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
