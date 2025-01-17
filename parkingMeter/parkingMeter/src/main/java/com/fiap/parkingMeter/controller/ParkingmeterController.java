package com.fiap.parkingMeter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.parkingMeter.domain.Parking;
import com.fiap.parkingMeter.domain.ParkingPrimaryKey;
import com.fiap.parkingMeter.domain.dto.ParkingDto;
import com.fiap.parkingMeter.repository.ParkingRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/parkingmeters")
public class ParkingmeterController {
    @Autowired
    private Validator validator;

    @Autowired
    private ParkingRepository parkingRepository;

    @PostMapping
    public ResponseEntity<?> registerParkingMeter(@RequestBody ParkingDto parkingRequest) {
        Map<Path, String> violationsMap = validate(parkingRequest);

        if (!violationsMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violationsMap);
        } else {

            Parking parking = parkingRequest.toParking();
            ParkingPrimaryKey parkingPrimaryKey = new ParkingPrimaryKey(parkingRepository.getMaxParkingId()+ 1);
            parking.setParkingIdentifierCode(parkingPrimaryKey);
            parkingRepository.save(parking);
            return ResponseEntity.status(HttpStatus.CREATED).body(parking);
        }
    }

    @GetMapping(value = { "/id/{id}" })
    public ResponseEntity<?> findParkingMeterById(@PathVariable int id) {

        Optional<Parking> optionalParking = parkingRepository.findById(new ParkingPrimaryKey(id));
        if (optionalParking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Parking meter not found for the provided ID.");
        } else {
            return ResponseEntity.ok(optionalParking.get());
        }

    }
    
    @GetMapping(value = { "/" })
    public ResponseEntity<Collection<Parking>> findAll() {
        var parkings = parkingRepository.findAll();
        return ResponseEntity.ok(parkings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParkingMeterById(@PathVariable int id) {

    	ParkingPrimaryKey parkingPrimaryKey = new ParkingPrimaryKey(id);
        Optional<Parking> optionalParking = parkingRepository.findById(parkingPrimaryKey);
        if (optionalParking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking meter not found for deletion.");
        } else {
        	parkingRepository.deleteById(parkingPrimaryKey);
            return ResponseEntity.ok("Parking meter successfully deleted.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParking(
            @PathVariable int id,
            @RequestBody ParkingDto parkingRequest) {
        Map<Path, String> violationsMap = validate(parkingRequest);
        ParkingPrimaryKey parkingPrimaryKey = new ParkingPrimaryKey(id);

        if (!violationsMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violationsMap);
        } else {
            Optional<Parking> existingParkingMeter = parkingRepository.findById(parkingPrimaryKey);
            if (existingParkingMeter.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body("Parking meter not found for update.");
            } else {
                Parking parkingMeter = parkingRequest.toParking();
                Parking updatedParkingMeter = existingParkingMeter.get();
                updatedParkingMeter.setCnpjParking(parkingMeter.getCnpjParking());
                updatedParkingMeter.setName(parkingMeter.getName());
                updatedParkingMeter.setTelephoneParking(parkingMeter.getTelephoneParking());
                updatedParkingMeter.setEmailParking(parkingMeter.getEmailParking());
                updatedParkingMeter.setStreetAddressParking(parkingMeter.getStreetAddressParking());
                updatedParkingMeter.setNeighborhoodAddressParking(parkingMeter.getNeighborhoodAddressParking());                
                updatedParkingMeter.setCityAddressParking(parkingMeter.getCityAddressParking());
                updatedParkingMeter.setHourlyParkingFee(parkingMeter.getHourlyParkingFee());

                parkingRepository.save(updatedParkingMeter);
                return ResponseEntity.ok(updatedParkingMeter);
            }
        }
    }
    private <T> Map<Path, String> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        Map<Path, String> violationsMap = violations.stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath(), violation -> violation.getMessage()));
        return violationsMap;
    }
}



