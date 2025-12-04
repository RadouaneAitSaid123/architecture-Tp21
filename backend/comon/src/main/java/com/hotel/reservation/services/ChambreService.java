package com.hotel.reservation.services;

import com.hotel.reservation.entities.Chambre;
import com.hotel.reservation.entities.TypeChambre;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author radouane
 **/
public interface ChambreService {

    Chambre createChambre(Chambre chambre);
    Optional<Chambre> getChambreById(Long id);
    List<Chambre> getAllChambres();
    List<Chambre> getChambresDisponibles();
    List<Chambre> getChambresByType(TypeChambre type);
    Chambre updateChambre(Long id, TypeChambre type, BigDecimal prix, Boolean disponible);
    void updateDisponibilite(Long id, boolean disponible);
    void deleteChambre(Long id);
    long count();
}
