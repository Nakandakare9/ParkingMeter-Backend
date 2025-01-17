package com.fiap.parkingMeter.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPrimaryKey;
import com.fiap.parkingMeter.domain.PaymentMethod;
import com.fiap.parkingMeter.domain.PaymentMethodPrimaryKey;
import com.fiap.parkingMeter.domain.dto.PaymentMethodDto;
import com.fiap.parkingMeter.repository.DriverRepository;
import com.fiap.parkingMeter.repository.PaymentMethodRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paymentmethods")
public class PaymentMethodController {

    @Autowired
    private Validator validator;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping
    public ResponseEntity<?> registerPaymentMethod(@RequestBody PaymentMethodDto paymentMethodRequest) {
        Map<Path, String> violationsMap = validate(paymentMethodRequest);

        if (!violationsMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violationsMap);
        } else {

            PaymentMethod paymentMethod = paymentMethodRequest.toPaymentMethod();

            Optional<Driver> optionalDriver = driverRepository.findById(paymentMethod.getDriver().getCpfDriver());
            if (optionalDriver.isEmpty()) {
                return ResponseEntity.badRequest().body("Registration not allowed: Driver CPF does not exist in the database.");
            }

            paymentMethod.setDriver(optionalDriver.get());
            String driverCpf = paymentMethod.getId().getDriverCpf().getCpfDriver();
            paymentMethod.setId(new PaymentMethodPrimaryKey(paymentMethod.getId().getDriverCpf(), paymentMethodRepository.getMaxPaymentMethodSequenceNumber(driverCpf) + 1));
            paymentMethodRepository.save(paymentMethod);
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethod);
        }
    }

    @GetMapping(value = { "/cpfsequence/{driverCpf}/{paymentMethodSequenceNumber}" })
    public ResponseEntity<?> getPaymentMethod(@PathVariable String driverCpf, @PathVariable int paymentMethodSequenceNumber) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(new PaymentMethodPrimaryKey(new DriverPrimaryKey(driverCpf), paymentMethodSequenceNumber));
        if (paymentMethodOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment method not found for the provided CPF and sequence number.");
        } else {
            return ResponseEntity.ok(paymentMethodOptional.get());
        }
    }

    @GetMapping(value = { "/driver/{driverCpf}" })
    public ResponseEntity<?> getDriverPaymentMethods(@PathVariable String driverCpf) {
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findPaymentMethodsByDriverCpf(driverCpf);
        if (paymentMethodList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No payment methods found for the provided driver CPF.");
        }
        return ResponseEntity.ok(paymentMethodList);
    }

    @GetMapping(value = { "/" })
    public ResponseEntity<Collection<PaymentMethod>> findAll() {
        var paymentMethods = paymentMethodRepository.findAll();
        return ResponseEntity.ok(paymentMethods);
    }

    @DeleteMapping("/{driverCpf}/{paymentMethodSequenceNumber}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable String driverCpf, @PathVariable int paymentMethodSequenceNumber) {
        PaymentMethodPrimaryKey primaryKey = new PaymentMethodPrimaryKey(new DriverPrimaryKey(driverCpf), paymentMethodSequenceNumber);
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(primaryKey);
        if (paymentMethodOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment method not found for the provided CPF and sequence number.");
        }
        paymentMethodRepository.deleteById(primaryKey);
        return ResponseEntity.ok("Driver's payment method successfully deleted.");
    }

    @PutMapping("/{driverCpf}/{paymentMethodSequenceNumber}")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable String driverCpf, @PathVariable int paymentMethodSequenceNumber, @RequestBody PaymentMethodDto paymentMethodRequest) {

        Map<Path, String> violationsMap = validate(paymentMethodRequest);
        if (!violationsMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violationsMap);
        } else {

            PaymentMethodPrimaryKey primaryKey = new PaymentMethodPrimaryKey(new DriverPrimaryKey(driverCpf), paymentMethodSequenceNumber);
            Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(primaryKey);
            if (paymentMethodOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment method not found for the provided CPF and sequence number.");
            }
            PaymentMethod paymentMethod = paymentMethodRequest.toPaymentMethod();
            PaymentMethod updatedPaymentMethod = paymentMethodOptional.get();            
            updatedPaymentMethod.setId(primaryKey);
            
            Optional<Driver> optionalDriver = driverRepository.findById(paymentMethod.getDriver().getCpfDriver());
            if (optionalDriver.isEmpty()) {
                return ResponseEntity.badRequest().body("Driver provided for updating the payment method not found.");
            }
            updatedPaymentMethod.setDriver(optionalDriver.get());
            updatedPaymentMethod.setPaymentMethodTypeCode(paymentMethod.getPaymentMethodTypeCode());
            updatedPaymentMethod.setPaymentMethodCardNumber(paymentMethod.getPaymentMethodCardNumber());
            updatedPaymentMethod.setPaymentMethodCardHolderName(paymentMethod.getPaymentMethodCardHolderName());          
            updatedPaymentMethod.setPaymentMethodCardExpirationMonth(paymentMethod.getPaymentMethodCardExpirationMonth());
            updatedPaymentMethod.setPaymentMethodCardExpirationYear(paymentMethod.getPaymentMethodCardExpirationYear());
            updatedPaymentMethod.setPaymentMethodCardSecurityCode(paymentMethod.getPaymentMethodCardSecurityCode());
            updatedPaymentMethod.setPreferredPaymentMethodIndicator(paymentMethod.getPreferredPaymentMethodIndicator());

            paymentMethodRepository.save(updatedPaymentMethod);
            return ResponseEntity.ok(updatedPaymentMethod);
        }
    }

    private <T> Map<Path, String> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        return violations.stream()
                .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }
}
