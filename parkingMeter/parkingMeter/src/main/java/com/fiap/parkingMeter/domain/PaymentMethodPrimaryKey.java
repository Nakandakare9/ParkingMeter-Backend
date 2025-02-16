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
public class PaymentMethodPrimaryKey implements Serializable {
    private DriverPrimaryKey driverCpf;
    private int paymentMethodSequenceNumber;
}