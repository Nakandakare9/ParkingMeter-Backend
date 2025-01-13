package com.fiap.parkingMeter.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fiap.parkingMeter.enums.TypeOptionTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class ParkingTransaction {
    @EmbeddedId
    @NonNull
    private ParkingTransactionPrimaryKey id;
    @MapsId("parkingIdentifierCode")
    @JoinColumn(name = "parking_identifier_code")
    @ManyToOne
    @NonNull
    @Setter
    private Parking parking;
    @MapsId("driverVehiclePrimaryKey")
    @JoinColumns({
            @JoinColumn(name = "cpf_driver"), //e99 atenção para nome da coluna
            @JoinColumn(name = "driver_vehicle_license_plate") //e99 atenção para nome da coluna
    })
    @ManyToOne
    @NonNull
    private DriverVehicle driverVehicle;
    @NonNull
    private TypeOptionTime typeOptionTime;
    private int desiredParkingDuration;
    private LocalDateTime parkingStartTime = LocalDateTime.now();
    private LocalDateTime parkingEndTime;
    private BigDecimal totalParkingValue;
    private int receiptNumber;
    
    public ParkingTransaction(ParkingTransactionPrimaryKey id, Parking parking, DriverVehicle driverVehicle, TypeOptionTime typeOptionTime, int desiredParkingDuration) {
        this.id = id;
        this.parking = parking;
        this.driverVehicle = driverVehicle;
        this.typeOptionTime = typeOptionTime;
        this.desiredParkingDuration = desiredParkingDuration;
    }
}