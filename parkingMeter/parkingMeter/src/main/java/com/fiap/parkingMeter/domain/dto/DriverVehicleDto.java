package com.fiap.parkingMeter.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.DriverVehiclePrimaryKey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class DriverVehicleDto {
	
	@JsonProperty
    @NotNull(message = "Driver's CPF field cannot be null.")
	private Driver driver;
	
	@JsonProperty
    @NotBlank(message = "The driver's vehicle license plate field cannot be blank or null.")
	private String driverVehicleLicensePlate;
	
	@JsonProperty
    @NotBlank(message = "Driver's vehicle brand field cannot be blank or null.")
	private String driverVehicleBrand;
	
	@JsonProperty
    @NotBlank(message = "Driver's vehicle model field cannot be blank or null.")
	private String driverVehicleModel;
	
	@JsonProperty
    @NotBlank(message = "Model year field of manufacture of the driver's vehicle cannot be blank or null.")
	private String yearOfManufactureDriverVehicle;
	
	@JsonProperty
    @NotBlank(message = "The driver's vehicle model year field cannot be blank or null.")
	private String modelYearVehicleDriver;
	
	public DriverVehicle toDriverVehicle() {
		return new DriverVehicle(
				new DriverVehiclePrimaryKey(driver.getCpfDriver(), driverVehicleLicensePlate),
				driver,
				driverVehicleBrand,
				driverVehicleModel,
				yearOfManufactureDriverVehicle,
				modelYearVehicleDriver);
	}
}
