package com.fiap.parkingMeter.domain;


import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DriverPaymentMethodPrimaryKey implements Serializable {
	private DriverPrimaryKey cpfDriver;
	private int sequentialNumberPaymentMethod;
}
