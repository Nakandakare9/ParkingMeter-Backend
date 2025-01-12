package com.fiap.parkingMeter.domain;


import com.fiap.parkingMeter.enums.PaymentMethodType;

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
public class DriverPaymentMethod {

	@EmbeddedId
	private DriverPaymentMethodPrimaryKey id;
	@MapsId("cpfDriver")
    @JoinColumn(name = "cpf_driver")
    @ManyToOne
	private Driver driver;
	private PaymentMethodType paymentMethodTypeCode;
	private String cardNumberPaymentMethod;
	private String nameCardHolderPaymentMethod;
	private int monthCardValidityPaymentMethod;
	private int yearCardValidityPaymentMethod;
	private int paymentMethodCardVerificationCode;
	private String selectedPaymentMethodIndicator;
	
}
