package com.example.railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.railway.entity.Passenger;
import com.example.railway.repository.PassengerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository repo;

    public List<Passenger> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public List<Passenger> filterByDate(LocalDate startDate, LocalDate endDate) {
        return repo.filterByDate(startDate, endDate);
    }

    public List<Passenger> listAllSorted(String sortOrder) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return repo.findAllByOrderByLastNameAsc();
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            return repo.findAllByOrderByLastNameDesc();
        }
        return repo.findAll();
    }

    public void save(Passenger passenger) {
        repo.save(passenger);
    }

    public Passenger get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> countPassengersByTrainId() {
        return repo.countPassengersByTrainId();
    }
}
