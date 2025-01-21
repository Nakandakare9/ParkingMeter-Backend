package com.fiap.parkingMeter.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.DriverVehiclePrimaryKey;
import com.fiap.parkingMeter.domain.dto.DriverVehicleDto;
import com.fiap.parkingMeter.repository.DriverRepository;
import com.fiap.parkingMeter.repository.DriverVehicleRepository;

import jakarta.validation.Validator;

@SpringBootTest
public class DriverVehicleControllerTest {

    @Mock
    private Validator validator;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverVehicleRepository driverVehicleRepository;

    @InjectMocks
    private DriverVehicleController driverVehicleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testRegisterDriverVehicle() {
    	DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        Driver driver = new Driver(driverPrimaryKey, "Soriano de Albuquerque", "(11)934567890", "soriano9@example.com", "Rua Camila Lima, 102", "Centro", "São Paulo");
         
        DriverVehicleDto driverVehicleDto = new DriverVehicleDto();
        driverVehicleDto.setDriver(driver);

        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setDriver(driver);

        when(validator.validate(any(DriverVehicleDto.class))).thenReturn(Collections.emptySet());
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(Optional.of(driver));
        when(driverVehicleRepository.findById(any(DriverVehiclePrimaryKey.class))).thenReturn(Optional.empty());
        when(driverVehicleRepository.save(any(DriverVehicle.class))).thenReturn(driverVehicle);

        ResponseEntity<?> response = driverVehicleController.registerDriverVehicle(driverVehicleDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testConsultDriverVehicle() {
        DriverVehicle driverVehicle = new DriverVehicle();
        Optional<DriverVehicle> driverVehicleOpt = Optional.of(driverVehicle);
        when(driverVehicleRepository.findById(any(DriverVehiclePrimaryKey.class))).thenReturn(driverVehicleOpt);

        ResponseEntity<?> response = driverVehicleController.consultDriverVehicle("379.497.818-82", "ABC-1234");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAll() {
        when(driverVehicleRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<DriverVehicle>> response = driverVehicleController.findAll();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testDeleteDriverVehicle() {
        DriverVehicle driverVehicle = new DriverVehicle();
        when(driverVehicleRepository.findById(any(DriverVehiclePrimaryKey.class))).thenReturn(Optional.of(driverVehicle));

        ResponseEntity<?> response = driverVehicleController.deleteDriverVehicle("379.497.818-82", "ABC-1234");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateDriverVehicle() {

    	DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        Driver driver = new Driver(driverPrimaryKey, "Soriano de Albuquerque", "(11)934567890", "soriano9@example.com", "Rua Camila Lima, 102", "Centro", "São Paulo");

        DriverVehicleDto driverVehicleDto = new DriverVehicleDto();
        driverVehicleDto.setDriver(driver);
        
        DriverVehiclePrimaryKey driverVehiclePrimaryKey = new DriverVehiclePrimaryKey(driverPrimaryKey, "ABC-1234");
        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setDriver(driver);
        driverVehicle.setId(driverVehiclePrimaryKey);

        when(validator.validate(any(DriverVehicleDto.class))).thenReturn(Collections.emptySet());
        when(driverVehicleRepository.findById(any(DriverVehiclePrimaryKey.class))).thenReturn(Optional.of(driverVehicle));
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(Optional.of(driver));

        ResponseEntity<?> response = driverVehicleController.updateDriver("123456789", "ABC1234", driverVehicleDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
