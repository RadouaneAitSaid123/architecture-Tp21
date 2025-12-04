package com.hotel.reservation.services;

import com.hotel.reservation.entities.Chambre;
import com.hotel.reservation.entities.Client;
import com.hotel.reservation.entities.Reservation;
import com.hotel.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author radouane
 **/
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final ChambreService chambreService;


    @Override
    public Reservation createReservation(Reservation reservation) {
        log.debug("Création d'une réservation pour le client {} et la chambre {} du {} au {}",
                reservation.getClient().getNom(), reservation.getChambre().getId(), reservation.getDateDebut(), reservation.getDateFin());

        // Validation des dates
        if (reservation.getDateDebut().isAfter(reservation.getDateFin())) {
            throw new IllegalArgumentException("La date de début doit être avant la date de fin");
        }

        if (reservation.getDateDebut().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le passé");
        }
// Récupérer le client
        Client client = clientService.getClientById(reservation.getClient().getId());
        // Récupérer la chambre
        Chambre chambre = chambreService.getChambreById(reservation.getChambre().getId())
                .orElseThrow(() -> new IllegalArgumentException("Chambre non trouvée avec l'ID : " + reservation.getChambre().getId()));

        // Vérifier la disponibilité de la chambre
        if (Boolean.FALSE.equals(chambre.getDisponible())) {
            throw new IllegalArgumentException("La chambre n'est pas disponible");
        }

        // Créer la réservation
        Reservation res = new Reservation();
        res.setClient(client);
        res.setChambre(chambre);
        res.setDateDebut(reservation.getDateDebut());
        res.setDateFin(reservation.getDateFin());
        res.setPreferences(reservation.getPreferences());

        Reservation savedReservation = reservationRepository.save(res);

        // Marquer la chambre comme non disponible
        chambre.setDisponible(false);
        chambreService.createChambre(chambre);

        return savedReservation;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        log.debug("Récupération de la réservation avec l'ID : {}", id);
        return reservationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        log.debug("Récupération de toutes les réservations");
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByClientId(Long clientId) {
        log.debug("Récupération des réservations pour le client : {}", clientId);
        return reservationRepository.findByClientId(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByChambreId(Long chambreId) {
        log.debug("Récupération des réservations pour la chambre : {}", chambreId);
        return reservationRepository.findByChambreId(chambreId);
    }

    @Override
    public Reservation updateReservation(Long id, Long clientId, Long chambreId, LocalDate dateDebut, LocalDate dateFin) {
        log.debug("Mise à jour de la réservation avec l'ID : {}", id);

        // Validation des dates
        if (dateDebut.isAfter(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être avant la date de fin");
        }

        // Récupérer la réservation existante
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée avec l'ID : " + id));

        // Libérer l'ancienne chambre si elle change
        if (!reservation.getChambre().getId().equals(chambreId)) {
            Chambre ancienneChambre = reservation.getChambre();
            ancienneChambre.setDisponible(true);
            chambreService.createChambre(ancienneChambre);
        }

        // Récupérer le nouveau client
        Client client = clientService.getClientById(clientId);

        // Récupérer la nouvelle chambre
        Chambre chambre = chambreService.getChambreById(chambreId)
                .orElseThrow(() -> new IllegalArgumentException("Chambre non trouvée avec l'ID : " + chambreId));

        // Vérifier la disponibilité de la nouvelle chambre
        if (!chambre.getId().equals(reservation.getChambre().getId()) && !chambre.getDisponible()) {
            throw new IllegalArgumentException("La nouvelle chambre n'est pas disponible");
        }

        // Mettre à jour la réservation
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);

        Reservation updatedReservation = reservationRepository.save(reservation);

        // Marquer la nouvelle chambre comme non disponible
        chambre.setDisponible(false);
        chambreService.createChambre(chambre);

        return updatedReservation;
    }

    @Override
    public void deleteReservation(Long id) {
        log.debug("Suppression de la réservation avec l'ID : {}", id);

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée avec l'ID : " + id));

        // Libérer la chambre
        Chambre chambre = reservation.getChambre();
        chambre.setDisponible(true);
        chambreService.createChambre(chambre);

        reservationRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return reservationRepository.count();
    }
}
