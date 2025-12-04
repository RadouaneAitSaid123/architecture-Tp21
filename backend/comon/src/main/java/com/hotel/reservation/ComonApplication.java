package com.hotel.reservation;

import com.hotel.reservation.entities.Chambre;
import com.hotel.reservation.entities.Client;
import com.hotel.reservation.entities.Reservation;
import com.hotel.reservation.entities.TypeChambre;
import com.hotel.reservation.services.ChambreService;
import com.hotel.reservation.services.ClientService;
import com.hotel.reservation.services.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
@Slf4j
public class ComonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComonApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReservationService reservationService,
                           ClientService clientService,
                           ChambreService chambreService) {
        return args -> {
            log.info("🚀 Initialisation des données de test...");


            // Insertion des clients
            log.info("📝 Insertion des clients...");
            Client c1 = clientService.createClient(
                    new Client(null, "Dupont", "Jean", "jean.dupont@example.com", "0611223344"));
            Client c2 = clientService.createClient(
                    new Client(null, "Martin", "Sofia", "sofia.martin@example.com", "0677889900"));
            log.info("✅ {} clients insérés", clientService.count());

            // Insertion des chambres
            log.info("🛏️ Insertion des chambres...");
            Chambre ch1 = chambreService.createChambre(
                    new Chambre(null, TypeChambre.SIMPLE, new BigDecimal("500.00"), true));
            Chambre ch2 = chambreService.createChambre(
                    new Chambre(null, TypeChambre.DOUBLE, new BigDecimal("800.00"), true));
            Chambre ch3 = chambreService.createChambre(
                    new Chambre(null, TypeChambre.SUITE, new BigDecimal("1500.00"), true));
            Chambre ch4 = chambreService.createChambre(
                    new Chambre(null, TypeChambre.FAMILIALE, new BigDecimal("1200.00"), true));
            log.info("✅ {} chambres insérées", chambreService.count());

            // Insertion des réservations
            log.info("📅 Insertion des réservations...");
            Reservation r1 = new Reservation(
                    null,
                    c1,
                    ch2,
                    LocalDate.now().plusDays(3),
                    LocalDate.now().plusDays(7),
                    "Étage élevé, chambre calme, lit double"
            );

            Reservation r2 = new Reservation(
                    null,
                    c2,
                    ch3,
                    LocalDate.now().plusDays(10),
                    LocalDate.now().plusDays(14),
                    "Vue mer, non-fumeur"
            );

            reservationService.createReservation(r1);
            reservationService.createReservation(r2);

            log.info("✅ {} réservations insérées", reservationService.count());
            log.info("✅ Initialisation des données terminée !");
        };
    }
}
