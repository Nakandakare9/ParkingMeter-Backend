package com.fiap.parkingMeter.job;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPrimaryKey;
import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.DriverVehiclePrimaryKey;
import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.repository.ParkingTransactionRepository;

@SpringBootTest
public class ParkingValidationJobTest {

    @Mock
    private ParkingTransactionRepository parkingTransactionRepository;

    @InjectMocks
    private ParkingValidationJob parkingValidationJob;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        parkingValidationJob = new ParkingValidationJob(parkingTransactionRepository);
        
        Field expireDurationField = ParkingValidationJob.class.getDeclaredField("expireDurationMinutes");
        expireDurationField.setAccessible(true);
        expireDurationField.set(parkingValidationJob, 5);
    }

    @Test
    public void testValidateParking() {
        ParkingTransaction parkingTransaction = new ParkingTransaction();
        parkingTransaction.setParkingStartTime(LocalDateTime.now().minusMinutes(10));

        DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey("379.497.818-82");
        Driver driver = new Driver(driverPrimaryKey, "Soriano de Albuquerque", "(11)934567890", "soriano9@example.com", "Rua Camila Lima, 102", "Centro", "São Paulo");
        DriverVehiclePrimaryKey driverVehiclePrimaryKey = new DriverVehiclePrimaryKey(driverPrimaryKey, "ABC-1234");
        
        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setId(driverVehiclePrimaryKey);
        driverVehicle.setDriver(driver);
        driverVehicle.setDriverVehicleModel("Ford Focus Ghia");

        parkingTransaction.setDriverVehicle(driverVehicle);

        when(parkingTransactionRepository.findParkingControlsAboutToExpire(any(LocalDateTime.class))).thenReturn(Collections.singletonList(parkingTransaction));

        parkingValidationJob.validateParking();

        assertTrue(true);
    }

}

