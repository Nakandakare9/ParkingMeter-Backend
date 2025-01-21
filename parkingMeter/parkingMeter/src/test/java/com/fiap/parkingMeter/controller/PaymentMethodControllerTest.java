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
import com.fiap.parkingMeter.domain.PaymentMethod;
import com.fiap.parkingMeter.domain.PaymentMethodPrimaryKey;
import com.fiap.parkingMeter.domain.dto.PaymentMethodDto;
import com.fiap.parkingMeter.repository.DriverRepository;
import com.fiap.parkingMeter.repository.PaymentMethodRepository;

import jakarta.validation.Validator;

@SpringBootTest
public class PaymentMethodControllerTest {

    @Mock
    private Validator validator;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private PaymentMethodController paymentMethodController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testRegisterPaymentMethod() {
        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
        DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        Driver driver = new Driver(driverPrimaryKey, "Soriano de Albuquerque", "(11)934567890", "soriano9@example.com", "Rua Camila Lima, 102", "Centro", "São Paulo");

        paymentMethodDto.setDriver(driver);
        paymentMethodDto.setPaymentMethodTypeCode(1);
        paymentMethodDto.setPaymentMethodCardNumber("5555555555554444");
        paymentMethodDto.setPaymentMethodCardholderName("SORIANO ALBUQUERQUE");
        paymentMethodDto.setPaymentMethodCardExpirationMonth(12);
        paymentMethodDto.setPaymentMethodCardExpirationYear(2030);
        paymentMethodDto.setPaymentMethodCardSecurityCode(123);
        paymentMethodDto.setPreferredPaymentMethodIndicator("Y");

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDriver(driver);

        when(validator.validate(any(PaymentMethodDto.class))).thenReturn(Collections.emptySet());
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(Optional.of(driver));
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);

        ResponseEntity<?> response = paymentMethodController.registerPaymentMethod(paymentMethodDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetPaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod();
        Optional<PaymentMethod> paymentMethodOpt = Optional.of(paymentMethod);
        when(paymentMethodRepository.findById(any(PaymentMethodPrimaryKey.class))).thenReturn(paymentMethodOpt);

        ResponseEntity<?> response = paymentMethodController.getPaymentMethod("123456789", 1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindAll() {
        when(paymentMethodRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<PaymentMethod>> response = paymentMethodController.findAll();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testDeletePaymentMethod() {
        PaymentMethod paymentMethod = new PaymentMethod();
        when(paymentMethodRepository.findById(any(PaymentMethodPrimaryKey.class))).thenReturn(Optional.of(paymentMethod));

        ResponseEntity<?> response = paymentMethodController.deletePaymentMethod("123456789", 1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePaymentMethod() {
    	PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
    	DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        Driver driver = new Driver(driverPrimaryKey, "Soriano de Albuquerque", "(11)934567890", "soriano9@example.com", "Rua Camila Lima, 102", "Centro", "São Paulo");

        paymentMethodDto.setDriver(driver);
        paymentMethodDto.setPaymentMethodTypeCode(1);
        paymentMethodDto.setPaymentMethodCardNumber("5555555555554444");
        paymentMethodDto.setPaymentMethodCardholderName("SORIANO ALBUQUERQUE");
        paymentMethodDto.setPaymentMethodCardExpirationMonth(12);
        paymentMethodDto.setPaymentMethodCardExpirationYear(2030);
        paymentMethodDto.setPaymentMethodCardSecurityCode(123);
        paymentMethodDto.setPreferredPaymentMethodIndicator("Y");
        
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDriver(driver);

        when(validator.validate(any(PaymentMethodDto.class))).thenReturn(Collections.emptySet());
        when(paymentMethodRepository.findById(any(PaymentMethodPrimaryKey.class))).thenReturn(Optional.of(paymentMethod));
        when(driverRepository.findById(any(DriverPrimaryKey.class))).thenReturn(Optional.of(driver));
        
        ResponseEntity<?> response = paymentMethodController.updatePaymentMethod("123456789", 1, paymentMethodDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
