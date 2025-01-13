package com.fiap.parkingMeter.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParkingTransactionPrimaryKey implements Serializable {
    private ParkingPrimaryKey parkingIdentifierCode;
    private DriverVehiclePrimaryKey driverVehiclePrimaryKey;
    private int parkingTransactionSequenceNumber;
}
