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
public class PaymentMethod {
    @EmbeddedId
    private PaymentMethodPrimaryKey id;

    @MapsId("driverCpf")
    @JoinColumn(name = "driver_cpf")
    @ManyToOne
    private Driver driver;

    private PaymentMethodType paymentMethodTypeCode;
    private String paymentMethodCardNumber;
    private String paymentMethodCardHolderName;
    private int paymentMethodCardExpirationMonth;
    private int paymentMethodCardExpirationYear;
    private int paymentMethodCardSecurityCode;
    private String preferredPaymentMethodIndicator;
}