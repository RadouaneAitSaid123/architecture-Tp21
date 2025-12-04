package com.hotel.reservation.services;

import com.hotel.reservation.entities.Chambre;
import com.hotel.reservation.entities.TypeChambre;
import com.hotel.reservation.repositories.ChambreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author radouane
 **/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChambreServiceImpl implements ChambreService {

    private final ChambreRepository chambreRepository;

    @Override
    public Chambre createChambre(Chambre chambre) {
        log.debug("Création d'une nouvelle chambre de type " + chambre.getType() + " à " + chambre.getPrix() + " DH");

        Chambre savedChambre = new Chambre();
        savedChambre.setType(chambre.getType());
        savedChambre.setPrix(chambre.getPrix());
        savedChambre.setDisponible(true);

        return chambreRepository.save(chambre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Chambre> getChambreById(Long id) {
        log.debug("Récupération de la chambre avec l'ID : {}", id);
        return chambreRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chambre> getAllChambres() {
        log.debug("Récupération de toutes les chambres");
        return chambreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chambre> getChambresDisponibles() {
        log.debug("Récupération des chambres disponibles");
        return chambreRepository.findByDisponible(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chambre> getChambresByType(TypeChambre type) {
        log.debug("Récupération des chambres de type : {}", type);
        return chambreRepository.findByType(type);
    }

    @Override
    public Chambre updateChambre(Long id, TypeChambre type, BigDecimal prix, Boolean disponible) {
        log.debug("Mise à jour de la chambre avec l'ID : {}", id);

        Chambre chambre = chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre non trouvée avec l'ID : " + id));

        chambre.setType(type);
        chambre.setPrix(prix);
        chambre.setDisponible(disponible);

        return chambreRepository.save(chambre);
    }

    @Override
    public void updateDisponibilite(Long id, boolean disponible) {
        log.debug("Mise à jour de la disponibilité de la chambre {} : {}", id, disponible);

        Chambre chambre = chambreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chambre non trouvée avec l'ID : " + id));

        chambre.setDisponible(disponible);
        chambreRepository.save(chambre);
    }

    @Override
    public void deleteChambre(Long id) {
        log.debug("Suppression de la chambre avec l'ID : {}", id);

        if (!chambreRepository.existsById(id)) {
            throw new IllegalArgumentException("Chambre non trouvée avec l'ID : " + id);
        }

        chambreRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return chambreRepository.count();
    }
}
