package com.aitsaid.restapi.controller;

import com.hotel.reservation.entities.Reservation;
import com.hotel.reservation.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author radoaune
 **/
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Reservation created = reservationService.createReservation(reservation);
        return ResponseEntity.ok(created);
    }

    // READ all
    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAllReservations();
    }

    // READ by id
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE (dates + client/chambre par id)
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(
            @PathVariable Long id,
            @RequestParam Long clientId,
            @RequestParam Long chambreId,
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {

        Reservation updated = reservationService.updateReservation(
                id,
                clientId,
                chambreId,
                LocalDate.parse(dateDebut),
                LocalDate.parse(dateFin)
        );
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
