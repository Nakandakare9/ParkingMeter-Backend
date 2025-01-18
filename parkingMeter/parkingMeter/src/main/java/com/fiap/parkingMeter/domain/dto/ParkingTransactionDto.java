package com.fiap.parkingMeter.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.Parking;
import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.domain.ParkingTransactionPrimaryKey;
import com.fiap.parkingMeter.enums.TypeOptionTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ParkingTransactionDto {
    @JsonProperty
    @NotNull(message = "Parking ID field cannot be null.")
    private Parking parking;
    @JsonProperty
    @NotNull(message = "Driver's CPF and vehicle's license plate cannot be null.")
    private DriverVehicle driverVehicle;
    @JsonProperty
    @Min(value = 0, message = "Parking transaction time option field must be 0 or 1.")
    @Max(value = 1, message = "Parking transaction time option field must be 0 or 1.")
    @NotNull(message = "Parking transaction time option field cannot be null.")
    private int timeOptionCode;
    @JsonProperty
    @NotNull(message = "Driver's desired parking duration field cannot be null.")
    private int desiredParkingDuration;

    public ParkingTransaction toParkingTransaction() {
        return new ParkingTransaction(
                new ParkingTransactionPrimaryKey(
                        parking.getParkingIdentifierCode(),
                        driverVehicle.getId(), 0),
                parking,
                driverVehicle,
                TypeOptionTime.values()[timeOptionCode],
                desiredParkingDuration);
    }
}