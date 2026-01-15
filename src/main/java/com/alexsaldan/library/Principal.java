package com.alexsaldan.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Principal {
    public static void main(String[] args) {
        SpringApplication.run(Principal.class, args);
        System.out.println("Spring Boot 3.4 iniciado com Java 25 no Ubuntu!");
    }
}
