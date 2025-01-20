package com.fiap.parkingMeter.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.repository.ParkingTransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ParkingValidationJob {
    private final ParkingTransactionRepository parkingTransactionRepository;

    @Value("${parking.validation.expireDuration:5}") 
    private int expireDurationMinutes;

    @Value("${parking.validation.fixedDelay:3000}") 
    private long fixedDelay;

    public ParkingValidationJob(ParkingTransactionRepository parkingTransactionRepository) {
        this.parkingTransactionRepository = parkingTransactionRepository;
    }

    @Scheduled(fixedDelayString = "${parking.validation.fixedDelay:3000}") 
    public void validateParking() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Checking parking");

        List<ParkingTransaction> parkingsExpire = parkingTransactionRepository.findParkingControlsAboutToExpire(now);

        for (ParkingTransaction parkingTransaction : parkingsExpire) {
            LocalDateTime expectedExpire = parkingTransaction.getParkingStartTime().plusMinutes(expireDurationMinutes);
            if (now.plusMinutes(5).isAfter(expectedExpire)) {
                sendAlert(parkingTransaction);
            }
        }
    }

    private void sendAlert(ParkingTransaction parkingTransaction) {
        System.out.println("--------------------------WARNING--------------------------");
        System.out.println("Warning: 5 minutes left before completing each parked hour.");
        System.out.println("Vehicle Model: " + parkingTransaction.getDriverVehicle().getDriverVehicleModel());
        System.out.println("Vehicle Plate: " + parkingTransaction.getDriverVehicle().getId().getDriverVehicleLicensePlate());
        System.out.println("Driver: " + parkingTransaction.getDriverVehicle().getDriver().getNameDriver());
        System.out.println("-----------------------------------------------------------");
    }
}