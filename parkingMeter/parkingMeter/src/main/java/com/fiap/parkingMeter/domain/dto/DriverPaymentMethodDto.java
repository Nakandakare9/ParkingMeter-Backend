package com.fiap.parkingMeter.domain.dto;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPaymentMethod;
import com.fiap.parkingMeter.domain.DriverPaymentMethodPrimaryKey;
import com.fiap.parkingMeter.enums.PaymentMethodType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DriverPaymentMethodDto {

	@JsonProperty
    @NotNull(message = "Driver's CPF field cannot be null.")
	private Driver driver;
	
	@JsonProperty
    @Min(value = 0, message = "Driver payment method type field must be between 0 and 2.")
    @Max(value = 2, message = "Driver payment method type field must be between 0 and 2.")
    @NotNull(message = "Driver payment method type field cannot be null.")
	private int paymentMethodTypeCode;
	
	@JsonProperty
    @NotBlank(message = "Card number field of the driver's payment method cannot be blank or null.")
    @CreditCardNumber(message = "Invalid card number")
	private String cardNumberPaymentMethod;
	
	@JsonProperty
    @NotBlank(message = "Cardholder name field of the driver's payment method cannot be blank or null.")
	private String nameCardHolderPaymentMethod;
	
	@JsonProperty
    @NotNull(message = "Month field of validity of the driver's payment method card cannot be null.")
	private int monthCardValidityPaymentMethod;
	
	@JsonProperty
    @NotNull(message = "Year field of validity of the driver's payment method card cannot be null.")
	private int yearCardValidityPaymentMethod;
	
	@JsonProperty
    @NotNull(message = "The driver's payment method card verification code field cannot be null.")
	private int paymentMethodCardVerificationCode;
	
	@JsonProperty
    @NotBlank(message = "Field indicating the driver's selected payment method cannot be blank or null.")
	private String selectedPaymentMethodIndicator;
	
	public DriverPaymentMethod toDriverPaymentMethod() {
		return new DriverPaymentMethod(
				new DriverPaymentMethodPrimaryKey(driver.getCpfDriver(), 0),
				driver,
				PaymentMethodType.values()[paymentMethodTypeCode],
				cardNumberPaymentMethod,
				nameCardHolderPaymentMethod,
				monthCardValidityPaymentMethod,
				yearCardValidityPaymentMethod,
				paymentMethodCardVerificationCode,
				selectedPaymentMethodIndicator);
	}
	
}
