package com.fiap.parkingMeter.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fiap.parkingMeter.domain.Parking;
import com.fiap.parkingMeter.domain.ParkingPrimaryKey;
import com.fiap.parkingMeter.domain.dto.ParkingDto;
import com.fiap.parkingMeter.repository.ParkingRepository;

import jakarta.validation.Validator;

@SpringBootTest
public class ParkingmeterControllerTest {

    @Mock
    private Validator validator;

    @Mock
    private ParkingRepository parkingRepository;

    @InjectMocks
    private ParkingmeterController parkingmeterController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testRegisterParkingMeter() {
        ParkingDto parkingDto = new ParkingDto();
        
        parkingDto.setCnpjParking("12.345.678/0001-95");
        parkingDto.setName("Parking Name");
        parkingDto.setTelephoneParking("(11)99456789");
        parkingDto.setEmailParking("email@parking.com");
        parkingDto.setStreetAddressParking("123 Parking St");
        parkingDto.setNeighborhoodAddressParking("Parking Neighborhood");
        parkingDto.setCityAddressParking("Parking City");
        parkingDto.setHourlyParkingFee(new BigDecimal("20.0"));
        
        Parking parking = new Parking();
        
        when(validator.validate(any(ParkingDto.class))).thenReturn(Collections.emptySet());
        when(parkingRepository.getMaxParkingId()).thenReturn(1);
        when(parkingRepository.save(any(Parking.class))).thenReturn(parking);

        ResponseEntity<?> response = parkingmeterController.registerParkingMeter(parkingDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testFindParkingMeterById() {
        Parking parking = new Parking();
        Optional<Parking> parkingOpt = Optional.of(parking);
        when(parkingRepository.findById(any(ParkingPrimaryKey.class))).thenReturn(parkingOpt);

        ResponseEntity<?> response = parkingmeterController.findParkingMeterById(1);
        assertNotNull(response);
    }

    @Test
    public void testFindAll() {
        when(parkingRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<Parking>> response = parkingmeterController.findAll();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    
    @Test
    public void testDeleteParkingMeterById() {
        Parking parking = new Parking();
        when(parkingRepository.findById(any(ParkingPrimaryKey.class))).thenReturn(Optional.of(parking));

        ResponseEntity<?> response = parkingmeterController.deleteParkingMeterById(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateParking() {
        ParkingDto parkingDto = new ParkingDto();
        
        parkingDto.setCnpjParking("12.345.678/0001-95");
        parkingDto.setName("Parking Name");
        parkingDto.setTelephoneParking("(11)99456789");
        parkingDto.setEmailParking("email@parking.com");
        parkingDto.setStreetAddressParking("123 Parking St");
        parkingDto.setNeighborhoodAddressParking("Parking Neighborhood");
        parkingDto.setCityAddressParking("Parking City");
        parkingDto.setHourlyParkingFee(new BigDecimal("20.0"));

        Parking parking = new Parking();
        parking.setCnpjParking("12.345.678/0001-95");
        parking.setName("Parking Name");
        parking.setTelephoneParking("(11)99456789");
        parking.setEmailParking("email@parking.com");
        parking.setStreetAddressParking("123 Parking St");
        parking.setNeighborhoodAddressParking("Parking Neighborhood");
        parking.setCityAddressParking("Parking City");
        parkingDto.setHourlyParkingFee(new BigDecimal("20.0"));
        
        when(validator.validate(any(ParkingDto.class))).thenReturn(Collections.emptySet());
        when(parkingRepository.findById(any(ParkingPrimaryKey.class))).thenReturn(Optional.of(parking));
        when(parkingRepository.save(any(Parking.class))).thenReturn(parking);
        
        ResponseEntity<?> response = parkingmeterController.updateParking(1, parkingDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
