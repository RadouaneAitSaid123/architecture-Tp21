package com.aitsaid.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.hotel.reservation.services",
        "com.hotel.reservation.repositories",
        "com.hotel.reservation.entities",
        "com.aitsaid.restapi.controller"
})
@EntityScan(basePackages = "com.hotel.reservation.entities")
@EnableJpaRepositories(basePackages = "com.hotel.reservation.repositories")
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

}
