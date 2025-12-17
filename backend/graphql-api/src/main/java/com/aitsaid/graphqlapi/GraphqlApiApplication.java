package com.aitsaid.graphqlapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.hotel.reservation.services",
        "com.hotel.reservation.repositories",
        "com.aitsaid.graphqlapi"
})
@EntityScan(basePackages = "com.hotel.reservation.entities")
@EnableJpaRepositories(basePackages = "com.hotel.reservation.repositories")
public class GraphqlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApiApplication.class, args);
    }

}
