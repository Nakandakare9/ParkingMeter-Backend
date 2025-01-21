package com.fiap.parkingMeter.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPrimaryKey;
import com.fiap.parkingMeter.domain.dto.DriverDto;
import com.fiap.parkingMeter.repository.DriverRepository;

import jakarta.validation.Validator;

@SpringBootTest
public class DriverControllerTest {

    @Mock
    private Validator validator;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverController driverController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    

    @Test
    public void testRegisterDriver() {
        DriverDto driverDto = new DriverDto(); 
        when(validator.validate(any(DriverDto.class))).thenReturn(Collections.emptySet());

        ResponseEntity<?> response = driverController.registerDriver(driverDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testConsultDriverCpf() {
        Driver driver = new Driver();
        Optional<Driver> driverOpt = Optional.of(driver);
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(driverOpt);

        ResponseEntity<?> response = driverController.consultDriverCpf("379.497.818-82");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAll() {
        when(driverRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<Driver>> response = driverController.findAll();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testDeleteDriverCpf() {
        Driver driver = new Driver();
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(Optional.of(driver));

        ResponseEntity<?> response = driverController.deleteDriverCpf("379.497.818-82");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateDriver() {
        DriverDto driverDto = new DriverDto();
        Driver driver = new Driver();
        when(validator.validate(any(DriverDto.class))).thenReturn(Collections.emptySet());
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(Optional.of(driver));

        ResponseEntity<?> response = driverController.updateDriver("379.497.818-82", driverDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
