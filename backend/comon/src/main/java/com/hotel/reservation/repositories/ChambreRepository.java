package com.hotel.reservation.repositories;

import com.hotel.reservation.entities.Chambre;
import com.hotel.reservation.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author radouane
 **/
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    List<Chambre> findByType(TypeChambre type);
}
