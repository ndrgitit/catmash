package org.ndrianja.catmash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class CatmashApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatmashApplication.class, args);
    }

    @Bean
    @Scope("singleton")
    public DataSource getCats() {
        return new DataSource();
    }

}
