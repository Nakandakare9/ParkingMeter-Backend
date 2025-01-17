package com.fiap.parkingMeter.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.PaymentMethod;
import com.fiap.parkingMeter.domain.PaymentMethodPrimaryKey;
import com.fiap.parkingMeter.enums.PaymentMethodType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.CreditCardNumber;

public class PaymentMethodDto {
    @JsonProperty
    @NotNull(message = "Driver CPF field cannot be null.")
    private Driver driver;
    @JsonProperty
    @Min(value = 0, message = "Driver payment method type field must be between 0 and 2.")
    @Max(value = 2, message = "Driver payment method type field must be between 0 and 2.")
    @NotNull(message = "Driver payment method type field cannot be null.")
    private int paymentMethodTypeCode;
    @JsonProperty
    @NotBlank(message = "Driver payment method card number field cannot be blank or null.")
    @CreditCardNumber(message = "Invalid card number.")
    private String paymentMethodCardNumber;
    @JsonProperty
    @NotBlank(message = "Driver payment method cardholder name field cannot be blank or null.")
    private String paymentMethodCardholderName;
    @JsonProperty
    @NotNull(message = "Driver payment method card expiration month field cannot be null.")
    private int paymentMethodCardExpirationMonth;
    @JsonProperty
    @NotNull(message = "Driver payment method card expiration year field cannot be null.")
    private int paymentMethodCardExpirationYear;
    @JsonProperty
    @NotNull(message = "Driver payment method card verification value field cannot be null.")
    private int paymentMethodCardSecurityCode;
    @JsonProperty
    @NotBlank(message = "Driver preferred payment method indicator field cannot be blank or null.")
    private String preferredPaymentMethodIndicator;
     
    public PaymentMethod toPaymentMethod() {
        return new PaymentMethod(
                new PaymentMethodPrimaryKey(driver.getCpfDriver(), 0),
                driver,
                PaymentMethodType.values()[paymentMethodTypeCode],
                paymentMethodCardNumber,
                paymentMethodCardholderName,
                paymentMethodCardExpirationMonth,
                paymentMethodCardExpirationYear,
                paymentMethodCardSecurityCode,
                preferredPaymentMethodIndicator);
    }
}