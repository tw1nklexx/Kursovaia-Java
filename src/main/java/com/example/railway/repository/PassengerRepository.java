package com.example.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.railway.entity.Passenger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query("SELECT p FROM Passenger p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.dateOfBirth, ' ', p.contactInfo) LIKE %?1%")
    List<Passenger> search(String keyword);

    @Query("SELECT p FROM Passenger p WHERE p.dateOfBirth >= :startDate AND p.dateOfBirth <= :endDate")
    List<Passenger> filterByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Passenger> findAllByOrderByLastNameAsc();
    List<Passenger> findAllByOrderByLastNameDesc();

    @Query("SELECT p.trainId AS trainId, COUNT(p) AS count FROM Passenger p GROUP BY p.trainId")
    List<Map<String, Object>> countPassengersByTrainId();
}
