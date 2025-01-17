package com.fiap.parkingMeter.domain;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force =true)
@RequiredArgsConstructor
@Getter
@Entity
public class Parking {
	
	@EmbeddedId
	private ParkingPrimaryKey parkingIdentifierCode;
	
	@Setter
	@NonNull
	private String cnpjParking;
	
	@Setter
	@NonNull
	private String name;
	
	@Setter
	@NonNull
	private String telephoneParking;
	
	@Setter
	@NonNull
	private String emailParking;
	
	@Setter
	@NonNull
	private String streetAddressParking;
	
	@Setter
	@NonNull
	private String neighborhoodAddressParking;
	
	@Setter
	@NonNull
	private String cityAddressParking;
	
	@Setter
	@NonNull
	private BigDecimal hourlyParkingFee;
	
	public void setParkingIdentifierCode(ParkingPrimaryKey parkingPrimaryKey) {
		parkingIdentifierCode = parkingPrimaryKey;
	}

}
