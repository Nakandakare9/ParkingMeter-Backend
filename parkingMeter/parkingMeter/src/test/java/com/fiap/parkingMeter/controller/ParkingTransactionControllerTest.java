package com.fiap.parkingMeter.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.fiap.parkingMeter.domain.DriverPrimaryKey;
import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.DriverVehiclePrimaryKey;
import com.fiap.parkingMeter.domain.Parking;
import com.fiap.parkingMeter.domain.ParkingPrimaryKey;
import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.domain.ParkingTransactionPrimaryKey;
import com.fiap.parkingMeter.domain.dto.ParkingTransactionDto;
import com.fiap.parkingMeter.enums.PaymentMethodType;
import com.fiap.parkingMeter.enums.TypeOptionTime;
import com.fiap.parkingMeter.repository.DriverVehicleRepository;
import com.fiap.parkingMeter.repository.ParkingRepository;
import com.fiap.parkingMeter.repository.ParkingTransactionRepository;
import com.fiap.parkingMeter.repository.PaymentMethodRepository;

import jakarta.validation.Validator;

@SpringBootTest
public class ParkingTransactionControllerTest {

    @Mock
    private Validator validator;

    @Mock
    private ParkingTransactionRepository parkingTransactionRepository;

    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private DriverVehicleRepository driverVehicleRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private ParkingTransactionController parkingTransactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testRegisterParkingTransaction() {
        ParkingTransactionDto parkingTransactionDto = new ParkingTransactionDto();

        ParkingPrimaryKey parkingPrimaryKey = new ParkingPrimaryKey(1);
        Parking parking = new Parking();
        parking.setParkingIdentifierCode(parkingPrimaryKey);

        DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        DriverVehiclePrimaryKey driverVehiclePrimaryKey = new DriverVehiclePrimaryKey(driverPrimaryKey, "ABC-1234");
        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setId(driverVehiclePrimaryKey);

        parkingTransactionDto.setParking(parking);
        parkingTransactionDto.setDriverVehicle(driverVehicle);

        when(validator.validate(any(ParkingTransactionDto.class))).thenReturn(Collections.emptySet());
        when(parkingRepository.findById(any(ParkingPrimaryKey.class))).thenReturn(Optional.of(parking));
        when(driverVehicleRepository.findById(any(DriverVehiclePrimaryKey.class))).thenReturn(Optional.of(driverVehicle));
        when(paymentMethodRepository.getPreferredPaymentMethodType(anyString())).thenReturn(PaymentMethodType.CREDIT.ordinal());
        when(parkingTransactionRepository.getMaxParkingTransactionSequenceNumber(anyInt(), anyString(), anyString())).thenReturn(1);
        when(parkingTransactionRepository.save(any(ParkingTransaction.class))).thenReturn(new ParkingTransaction());

        ResponseEntity<?> response = parkingTransactionController.registerParkingTransaction(parkingTransactionDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRegisterParkingTransactionWithPixAndVariableTimeOption() {
        ParkingTransactionDto parkingTransactionDto = new ParkingTransactionDto();

        ParkingPrimaryKey parkingPrimaryKey = new ParkingPrimaryKey(1);
        Parking parking = new Parking();
        parking.setParkingIdentifierCode(parkingPrimaryKey);

        DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        DriverVehiclePrimaryKey driverVehiclePrimaryKey = new DriverVehiclePrimaryKey(driverPrimaryKey, "ABC-1234");
        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setId(driverVehiclePrimaryKey);

        parkingTransactionDto.setParking(parking);
        parkingTransactionDto.setDriverVehicle(driverVehicle);
        parkingTransactionDto.setTimeOptionCode(0);

        when(validator.validate(any(ParkingTransactionDto.class))).thenReturn(Collections.emptySet());
        when(parkingRepository.findById(any(ParkingPrimaryKey.class))).thenReturn(Optional.of(parking));
        when(driverVehicleRepository.findById(any(DriverVehiclePrimaryKey.class))).thenReturn(Optional.of(driverVehicle));
        when(paymentMethodRepository.getPreferredPaymentMethodType(anyString())).thenReturn(PaymentMethodType.PIX.ordinal());

        ResponseEntity<?> response = parkingTransactionController.registerParkingTransaction(parkingTransactionDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }



    
}
