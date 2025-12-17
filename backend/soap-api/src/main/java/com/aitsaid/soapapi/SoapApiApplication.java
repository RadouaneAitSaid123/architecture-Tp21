package com.aitsaid.soapapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.hotel.reservation.services",
        "com.hotel.reservation.repositories",
        "com.aitsaid.soapapi"
})
@EntityScan(basePackages = "com.hotel.reservation.entities")
@EnableJpaRepositories(basePackages = "com.hotel.reservation.repositories")
public class SoapApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapApiApplication.class, args);
    }

}
