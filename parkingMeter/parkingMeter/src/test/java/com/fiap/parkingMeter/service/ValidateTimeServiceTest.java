package com.fiap.parkingMeter.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiap.parkingMeter.service.impl.ValidateTimeServiceImpl;

@SpringBootTest
public class ValidateTimeServiceTest {

    @InjectMocks
    private ValidateTimeServiceImpl validateTimeService;

    @BeforeEach
    public void setup() {
        validateTimeService = new ValidateTimeServiceImpl();
    }

    @Test
    public void testStartValidation() {
        LocalDateTime startTime = LocalDateTime.now();
        validateTimeService.startValidation(startTime);

        assertFalse(validateTimeService.isAlertSent());
    }

    @Test
    public void testValidateParking() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(55);
        validateTimeService.startValidation(startTime);

        try (MockedStatic<LocalDateTime> mockedNow = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedNow.when(LocalDateTime::now).thenReturn(startTime.plusMinutes(60));

            validateTimeService.validateParking();

            assertFalse(validateTimeService.isAlertSent());
        }
    }
}


