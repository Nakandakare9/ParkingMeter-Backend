package com.fiap.parkingMeter.domain.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.parkingMeter.domain.Parking;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

@Setter
public class ParkingDto {
	
	@JsonProperty
    @NotBlank(message = "Parking CNPJ field cannot be blank or null.")
    @CNPJ
	private String cnpjParking;
	
	@JsonProperty
    @NotBlank(message = "The parking lot's corporate name field cannot be blank or null.")
	private String name;
	
	@JsonProperty
    @NotBlank(message = "The parking phone number field cannot be blank or null.")
	private String telephoneParking;
	
	@JsonProperty
    @NotBlank(message = "Parking email field cannot be blank or null.")
    @Email(message = "Invalid email")
	private String emailParking;
	
	@JsonProperty
    @NotBlank(message = "Parking address street field cannot be blank or null.")
	private String streetAddressParking;

	@JsonProperty
    @NotBlank(message = "Parking address neighborhood field cannot be blank or null.")
	private String neighborhoodAddressParking;
	
	@JsonProperty
    @NotBlank(message = "Parking address city field cannot be blank or null.")
	private String cityAddressParking;
	
	@JsonProperty
    @NotNull(message = "Parking fee value field cannot be null.")
	private BigDecimal hourlyParkingFee;
	
	public Parking toParking() {
		return new Parking(cnpjParking,
				name,
				telephoneParking,
				emailParking,
				streetAddressParking,
				neighborhoodAddressParking,
				cityAddressParking,
				hourlyParkingFee);
	}

}
