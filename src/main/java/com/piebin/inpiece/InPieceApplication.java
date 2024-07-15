package com.piebin.inpiece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InPieceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InPieceApplication.class, args);
    }

}
