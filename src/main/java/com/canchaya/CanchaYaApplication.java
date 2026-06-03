package com.canchaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend CanchaYa.
 * Arranca con: mvn spring-boot:run
 */
@SpringBootApplication
public class CanchaYaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanchaYaApplication.class, args);
    }
}
