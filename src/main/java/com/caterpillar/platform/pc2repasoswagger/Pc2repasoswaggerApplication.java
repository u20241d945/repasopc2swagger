package com.caterpillar.platform.pc2repasoswagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Pc2repasoswaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pc2repasoswaggerApplication.class, args);
    }

}
