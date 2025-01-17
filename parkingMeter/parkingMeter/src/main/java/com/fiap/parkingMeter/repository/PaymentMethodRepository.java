package com.fiap.parkingMeter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.parkingMeter.domain.PaymentMethod;
import com.fiap.parkingMeter.domain.PaymentMethodPrimaryKey;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, PaymentMethodPrimaryKey> {
    @Query(value = "select ifnull(max(payment_method_sequence_number), 0)" +
            "from payment_method where driver_cpf = :driver_cpf", nativeQuery = true)
    int getMaxPaymentMethodSequenceNumber(@Param("driver_cpf") String driverCpf);

    @Query(value = "select * from payment_method where driver_cpf = :driver_cpf", nativeQuery = true)
    List<PaymentMethod> findPaymentMethodsByDriverCpf(@Param("driver_cpf") String driverCpf);

    @Query(value =
            "select payment_method_type_code " +
            "from payment_method " +
            "where driver_cpf = :driver_cpf " +
            "  and preferred_payment_method_indicator = 'Y'", nativeQuery = true)
    int getPreferredPaymentMethodType(@Param("driver_cpf") String driverCpf);
}