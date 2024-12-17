package com.example.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.railway.entity.Train;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    @Query("SELECT t FROM Train t WHERE CONCAT(t.trainNumber, ' ', t.departureDateTime, ' ', t.arrivalDateTime, ' ', t.departureStation, ' ', t.arrivalStation) LIKE %:keyword%")
    List<Train> search(@Param("keyword") String keyword);

    @Query("SELECT t FROM Train t WHERE t.departureDateTime >= :startDate AND t.departureDateTime <= :endDate")
    List<Train> filterByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Train> findAllByOrderByDepartureDateTimeAsc();

    List<Train> findAllByOrderByDepartureDateTimeDesc();

    @Query("SELECT DATE(t.departureDateTime) AS date, COUNT(t) AS count FROM Train t GROUP BY DATE(t.departureDateTime)")
    List<Map<String, Object>> countTrainsByDate();

    @Query("SELECT t.arrivalStation AS station, COUNT(t) AS count FROM Train t GROUP BY t.arrivalStation")
    List<Map<String, Object>> countTrainsByArrivalStation();

    @Query("SELECT t.departureStation AS station, COUNT(t) AS count FROM Train t GROUP BY t.departureStation")
    List<Map<String, Object>> countTrainsByStation();

    @Query("SELECT DISTINCT t.arrivalStation FROM Train t")
    List<String> findAllArrivalStations();

    List<Train> findByArrivalStation(String arrivalStation);
}
