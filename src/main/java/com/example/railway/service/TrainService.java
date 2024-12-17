package com.example.railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.railway.entity.Train;
import com.example.railway.repository.TrainRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public List<Train> listAll(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return trainRepository.search(keyword);
        }
        return trainRepository.findAll();
    }

    public List<Train> filterByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return trainRepository.filterByDate(startDate, endDate);
    }

    public List<Train> listAllSorted(String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return trainRepository.findAllByOrderByDepartureDateTimeAsc();
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            return trainRepository.findAllByOrderByDepartureDateTimeDesc();
        }
        return trainRepository.findAll();
    }

    public void save(Train train) {
        trainRepository.save(train);
    }

    public Train get(Long id) {
        return trainRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        trainRepository.deleteById(id);
    }

    public List<Map<String, Object>> countTrainsByDate() {
        return trainRepository.countTrainsByDate();
    }

    public List<Map<String, Object>> countTrainsByArrivalStation() {
        return trainRepository.countTrainsByArrivalStation();
    }

    public List<Map<String, Object>> countTrainsByStation() {
        return trainRepository.countTrainsByStation();
    }

    public List<String> findAllArrivalStations() {
        return trainRepository.findAllArrivalStations();
    }

    public List<Train> filterByArrivalStation(String arrivalStation) {
        return trainRepository.findByArrivalStation(arrivalStation);
    }

    public List<Train> listAll() {
        return trainRepository.findAll();
    }
}
