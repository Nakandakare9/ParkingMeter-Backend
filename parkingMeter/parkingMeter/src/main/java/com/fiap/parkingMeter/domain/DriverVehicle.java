package com.fiap.parkingMeter.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DriverVehicle {
	
	@EmbeddedId
	private DriverVehiclePrimaryKey id;
	
	@MapsId("cpfDriver")
    @JoinColumn(name = "cpf_driver")
    @ManyToOne
	private Driver driver;
	private String driverVehicleBrand;
	private String driverVehicleModel;
	private String yearOfManufactureDriverVehicle;
	private String modelYearVehicleDriver;

}
