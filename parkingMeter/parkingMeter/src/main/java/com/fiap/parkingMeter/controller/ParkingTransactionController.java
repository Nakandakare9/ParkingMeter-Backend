package com.fiap.parkingMeter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.Parking;
import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.domain.ParkingTransactionPrimaryKey;
import com.fiap.parkingMeter.domain.dto.ParkingTransactionDto;
import com.fiap.parkingMeter.enums.PaymentMethodType;
import com.fiap.parkingMeter.enums.TypeOptionTime;
import com.fiap.parkingMeter.repository.DriverPaymentMethodRepository;
import com.fiap.parkingMeter.repository.DriverVehicleRepository;
import com.fiap.parkingMeter.repository.ParkingRepository;
import com.fiap.parkingMeter.repository.ParkingTransactionRepository;

import java.util.Optional;
@RestController
@RequestMapping("/parking-transaction")
public class ParkingTransactionController {
	
	@Autowired
    private ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private DriverVehicleRepository driverVehicleRepository;

    @Autowired
    private DriverPaymentMethodRepository driverPaymentMethodRepository;

    @PostMapping
    public ResponseEntity<?> registerParkingControl(
            @RequestBody ParkingTransactionDto parkingTransactionlDto) {

    	ParkingTransaction parkingTransaction = parkingTransactionlDto.toParkingTransaction();

        Optional<Parking> optionalParking =
                parkingRepository.findById(
                		parkingTransaction.getParking().getParkingIdentifierCode());

        if (optionalParking.isEmpty()){
            return ResponseEntity.badRequest().
                    body("Registration not allowed: parking ID not found.");
        }

        Optional<DriverVehicle> optionalDriverVehicle =
                driverVehicleRepository.findById(
                		parkingTransaction.getDriverVehicle().getId());

        if (optionalDriverVehicle.isEmpty()){
            return ResponseEntity.badRequest().
                    body("Registration not allowed: vehicle not found for the provided CPF and license plate.");
        }

        parkingTransaction.setParking(optionalParking.get());
        parkingTransaction.setDriverVehicle(optionalDriverVehicle.get());

        int parkingId =
        		parkingTransaction.getParking().
                        getParkingIdentifierCode().
                        getParkingIdentifierCode();

        String driverCpf =
        		parkingTransaction.getDriverVehicle().
                        getId().getCpfDriver().getCpfDriver();

        String vehiclePlate =
        		parkingTransaction.getDriverVehicle().
                        getId().getDriverVehicleLicensePlate();
        
        int preferredPaymentMethodCode = driverPaymentMethodRepository.getTypePaymentMethodSelectedDriver(driverCpf);

        // If preferred payment method is PIX, do not allow parking control for variable time option
        if (preferredPaymentMethodCode == PaymentMethodType.PIX.ordinal() &&
        		parkingTransaction.getTypeOptionTime().ordinal() == TypeOptionTime.VARIABLE.ordinal()){
            return ResponseEntity.badRequest().
                    body("Registration not allowed: driver with PIX payment method can only use fixed time option.");
        }

        parkingTransaction.setId(
                new ParkingTransactionPrimaryKey(
                		parkingTransaction.getParking().getParkingIdentifierCode(),
                		parkingTransaction.getDriverVehicle().getId(),
                		parkingTransactionRepository.getMaxParkingTransactionSequenceNumber(
                                parkingId, driverCpf, vehiclePlate)+1));

        parkingTransactionRepository.save(parkingTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingTransaction);
    }
    

}

