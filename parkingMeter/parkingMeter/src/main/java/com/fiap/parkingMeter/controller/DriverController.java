package com.fiap.parkingMeter.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPrimaryKey;
import com.fiap.parkingMeter.domain.dto.DriverDto;
import com.fiap.parkingMeter.repository.DriverRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/drivers")
public class DriverController {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private DriverRepository driverRepository;
	
	@PostMapping
	public ResponseEntity<?> registerDriver(@RequestBody DriverDto driverDto) {
		Map<Path, String> violationsMap = validate(driverDto);
		
		if(!violationsMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violationsMap);
		} else {
			Driver driver = driverDto.toDriver();
			Optional<Driver> optionalDriver = driverRepository.findById(driver.getCpfDriver());
			
			if(optionalDriver.isPresent()) {
				return ResponseEntity.unprocessableEntity().body("Registration not permitted: Driver's CPF already found in the database.");
			}
			
			driverRepository.save(driver);
			return ResponseEntity.status(HttpStatus.CREATED).body(driver);
		}
	}
	
	@GetMapping(value= {"/cpf/{cpf}"})
	public ResponseEntity<?> consultDriverCpf(@PathVariable String cpf) {
		Optional<Driver> optionalDriver = driverRepository.findById(new DriverPrimaryKey(cpf));
		
		if(optionalDriver.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found for the CPF entered.");
		} else {
			return ResponseEntity.ok(optionalDriver.get());
		}
	}
	
	@GetMapping(value= {"/"})
	public ResponseEntity<Collection<Driver>> findAll() {
		var drivers = driverRepository.findAll();
		return ResponseEntity.ok(drivers);
	}
	
	@DeleteMapping("/{cpf}")
	public ResponseEntity<?> deleteDriverCpf(@PathVariable String cpf) {
		
		DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey(cpf);
		
		Optional<Driver> optionalDriver = driverRepository.findById(driverPrimaryKey);
		
		if(optionalDriver.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found for exclusion.");
		} else {
			 driverRepository.deleteById(driverPrimaryKey);
			 return ResponseEntity.ok("Driver successfully deleted.");
		}
	}
	
	
	@PutMapping("/{cpf}")
	public ResponseEntity<?> updateDriver(@PathVariable String cpf, @RequestBody DriverDto driverDto) {
		Map<Path, String> violationsMap = validate(driverDto);
		
		DriverPrimaryKey driverPrimaryKey = new DriverPrimaryKey(cpf);
		
		if(!violationsMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violationsMap);
		} else {
			Optional<Driver> driverExisting = driverRepository.findById(driverPrimaryKey);
			if(driverExisting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found for update.");
			} else {
				Driver driver = driverDto.toDriver();
				
				Driver driverUpdated = driverExisting.get();
				driverUpdated.setCpfDriver(driverPrimaryKey);
				driverUpdated.setNameDriver(driver.getNameDriver());
				driverUpdated.setCellphoneDriver(driver.getCellphoneDriver());
				driverUpdated.setEmailDriver(driver.getEmailDriver());
				driverUpdated.setStreetAddressDriver(driver.getStreetAddressDriver());
				driverUpdated.setNeighborhoodAddressDriver(driver.getNeighborhoodAddressDriver());
				driverUpdated.setCityAddressDriver(driver.getCityAddressDriver());
				
				driverRepository.save(driverUpdated);
				return ResponseEntity.ok(driverUpdated);
			}
		}
	}
	
	private <T> Map<Path, String> validate(T dto) {
		Set<ConstraintViolation<T>> violations = validator.validate(dto);
		Map<Path, String> violationsMap = violations.stream()
				.collect(Collectors.toMap(violation -> violation.getPropertyPath(), violation -> violation.getMessage()));
		return violationsMap;
	}

}
