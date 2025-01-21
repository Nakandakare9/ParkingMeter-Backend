package com.fiap.parkingMeter.domain.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPrimaryKey;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

@Setter
public class DriverDto {

	@JsonProperty
	@NotBlank(message= "The driver's CPF field cannot be blank or null.")
	@CPF
	private String cpfDriver;
	
	@JsonProperty
	@NotBlank(message= "Driver name field cannot be blank or null.")
	private String nameDriver;
	
	@JsonProperty
	@NotBlank(message= "Driver's cellphone number field cannot be blank or null.")
	private String cellphoneDriver;
	
	@JsonProperty
	@NotBlank(message= "The driver's email field cannot be blank or null.")
	@Email(message= "Invalid email.")
	private String emailDriver;
	
	@JsonProperty
	@NotBlank(message= "Street field for the driver's address cannot be blank or null.")
	private String streetAddressDriver;
	
	@JsonProperty
	@NotBlank(message= "The driver's address neighborhood field cannot be blank or null.")
	private String neighborhoodAddressDriver;
	
	@JsonProperty
	@NotBlank(message= "City field of the driver's address cannot be blank or null.")
	private String cityAddressDriver;
	
	public Driver toDriver() {
		return new Driver(new DriverPrimaryKey(cpfDriver),
			nameDriver,
			cellphoneDriver,
			emailDriver,
			streetAddressDriver,
			neighborhoodAddressDriver,
			cityAddressDriver);
	}	
	
}
