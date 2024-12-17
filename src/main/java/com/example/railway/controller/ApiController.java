package com.example.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.railway.entity.Train;
import com.example.railway.entity.Passenger;
import com.example.railway.service.CustomUserDetailsService;
import com.example.railway.service.TrainService;
import com.example.railway.service.PassengerService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private PassengerService passengerService;

    @PostMapping(value = "/trains", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Train> saveTrain(Train train) {
        if (train.getTrainNumber() == null || train.getDepartureDateTime() == null ||
                train.getArrivalDateTime() == null || train.getDepartureStation() == null ||
                train.getArrivalStation() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        trainService.save(train);
        return new ResponseEntity<>(train, HttpStatus.CREATED);
    }

    @GetMapping("/trains/{id}")
    public ResponseEntity<Train> getTrain(@PathVariable Long id) {
        Train train = trainService.get(id);
        if (train == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(train, HttpStatus.OK);
    }

    @PatchMapping("/trains/{id}")
    public ResponseEntity<Train> patchTrain(@PathVariable Long id, @RequestBody Train train) {
        Train existingTrain = trainService.get(id);
        if (existingTrain == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (train.getTrainNumber() != null) {
            existingTrain.setTrainNumber(train.getTrainNumber());
        }
        if (train.getDepartureDateTime() != null) {
            existingTrain.setDepartureDateTime(train.getDepartureDateTime());
        }
        if (train.getArrivalDateTime() != null) {
            existingTrain.setArrivalDateTime(train.getArrivalDateTime());
        }
        if (train.getDepartureStation() != null) {
            existingTrain.setDepartureStation(train.getDepartureStation());
        }
        if (train.getArrivalStation() != null) {
            existingTrain.setArrivalStation(train.getArrivalStation());
        }
        trainService.save(existingTrain);
        return new ResponseEntity<>(existingTrain, HttpStatus.OK);
    }

    @DeleteMapping("/trains/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/passengers", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Passenger> savePassenger(Passenger passenger) {
        passengerService.save(passenger);
        return new ResponseEntity<>(passenger, HttpStatus.CREATED);
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        Passenger passenger = passengerService.get(id);
        if (passenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @PatchMapping("/passengers/{id}")
    public ResponseEntity<Passenger> patchPassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        Passenger existingPassenger = passengerService.get(id);
        if (existingPassenger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (passenger.getFirstName() != null) {
            existingPassenger.setFirstName(passenger.getFirstName());
        }
        if (passenger.getLastName() != null) {
            existingPassenger.setLastName(passenger.getLastName());
        }
        if (passenger.getTrainId() != null) {
            existingPassenger.setTrainId(passenger.getTrainId());
        }
        if (passenger.getContactInfo() != null) {
            existingPassenger.setContactInfo(passenger.getContactInfo());
        }
        if (passenger.getDateOfBirth() != null) {
            existingPassenger.setDateOfBirth(passenger.getDateOfBirth());
        }

        passengerService.save(existingPassenger);
        return new ResponseEntity<>(existingPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/passengers/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

