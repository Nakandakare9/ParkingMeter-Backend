package com.fiap.parkingMeter.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver {
	@EmbeddedId
	private DriverPrimaryKey cpfDriver;
	private String nameDriver;
	private String cellphoneDriver;
	private String emailDriver;
	private String streetAddressDriver;
	private String neighborhoodAddressDriver;
	private String cityAddressDriver;
}
