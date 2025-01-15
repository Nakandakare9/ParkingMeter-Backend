package com.fiap.parkingMeter.controller;

import java.util.Collection;
import java.util.List;
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
import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.DriverVehiclePrimaryKey;
import com.fiap.parkingMeter.domain.dto.DriverVehicleDto;
import com.fiap.parkingMeter.repository.DriverRepository;
import com.fiap.parkingMeter.repository.DriverVehicleRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/vehicles")
public class DriverVehicleController {

	@Autowired
	private Validator validator;
	
	@Autowired
	private DriverVehicleRepository driverVehicleRepository;
	
	@Autowired
	private DriverRepository driverRepository;
	
	@PostMapping
	public ResponseEntity<?> registerDriverVehicle(@RequestBody DriverVehicleDto driverVehicleDto) {
		Map<Path, String> violationsMap = validate(driverVehicleDto);
		
		if(!violationsMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violationsMap);
		} else {
			DriverVehicle driverVehicle = driverVehicleDto.toDriverVehicle();
			
			Optional<Driver> optionalDriver = driverRepository.findById(driverVehicle.getDriver().getCpfDriver());
			
			if(optionalDriver.isEmpty()) {
				return ResponseEntity.badRequest().body("Registration not permitted: Vehicle driver's CPF does not exist on the base.");
			}
			
			Optional<DriverVehicle> optionalDriverVehicle = driverVehicleRepository.findById(driverVehicle.getId());
			
			if (optionalDriverVehicle.isPresent()) {
				return ResponseEntity.unprocessableEntity().body("Registration not permitted: Vehicle already registered for the informed driver.");
			}
			
			driverVehicle.setDriver(optionalDriver.get());
			driverVehicleRepository.save(driverVehicle);
			return ResponseEntity.status(HttpStatus.CREATED).body(driverVehicle);
		}
	}
	
	@GetMapping(value= {"/cpfplate/{cpfDriver}/{plateDriverVehicle}"})
	public ResponseEntity<?> consultDriverVehicle(@PathVariable String cpfDriver, @PathVariable String plateDriverVehicle) {
		Optional<DriverVehicle> optionalDriverVehicle = driverVehicleRepository.findById(new DriverVehiclePrimaryKey(new DriverPrimaryKey(cpfDriver), plateDriverVehicle));
		
		if(optionalDriverVehicle.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle license plate not found for the driver's CPF provided.");
		} else {
			return ResponseEntity.ok(optionalDriverVehicle.get());
		}
	}

	@GetMapping(value= {"/driver/{cpfDriver}"})
	public ResponseEntity<?> consultADriversVehicles(@PathVariable String cpfDriver) {
		List<DriverVehicle> driverVehicleList = driverVehicleRepository.findDriverVehicleByCpfDriver(cpfDriver);
		
		if(driverVehicleList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no vehicles for the driver's CPF number.");
		}
		
		return ResponseEntity.ok(driverVehicleList);
	}
	
	@GetMapping(value= {"/plate/{plateDriverVehicle}"})
	public ResponseEntity<?> consultVehiclesFromLicensePlate(@PathVariable String plateDriverVehicle) {
		 List<DriverVehicle> driverVehicleList = driverVehicleRepository.findDriverVehicleByLicensePlate(plateDriverVehicle);
		 
		 if(driverVehicleList.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no driver vehicles for the indicated license plate.");
		 }
		 
		 return ResponseEntity.ok(driverVehicleList);
	}

	@GetMapping(value= {"/"})
	public ResponseEntity<Collection<DriverVehicle>> findAll() {
		var vehicles = driverVehicleRepository.findAll();
		return ResponseEntity.ok(vehicles);
	}
	
	@DeleteMapping("/{cpfDriver}/{plateDriverVehicle}")
	public ResponseEntity<?> deleteDriverVehicle(@PathVariable String cpfDriver, @PathVariable String plateDriverVehicle) {
		DriverVehiclePrimaryKey driverVehiclePrimaryKey = new DriverVehiclePrimaryKey(new DriverPrimaryKey(cpfDriver), plateDriverVehicle);
		
		Optional<DriverVehicle> optionalDriverVehicle = driverVehicleRepository.findById(driverVehiclePrimaryKey);
		
		if(optionalDriverVehicle.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Placa do veículo não encontrada para o CPF do condutor informado.");
		} else {
			driverVehicleRepository.deleteById(driverVehiclePrimaryKey);
			return ResponseEntity.ok("Driver's vehicle successfully deleted.");
		}
	}
	
	@PutMapping("/{cpfDriver}/{plateDriverVehicle}")
	public ResponseEntity<?> updateDriver(@PathVariable String cpfDriver, @PathVariable String plateDriverVehicle, @RequestBody DriverVehicleDto driverVehicleDto) {
		Map<Path, String> violationsMap = validate(driverVehicleDto);
		
		if(!violationsMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violationsMap);
		} else {
			DriverVehiclePrimaryKey driverVehiclePrimaryKey = new DriverVehiclePrimaryKey(new DriverPrimaryKey(cpfDriver), plateDriverVehicle);
			
			Optional<DriverVehicle> driverVehicleExisting = driverVehicleRepository.findById(driverVehiclePrimaryKey);
			
			if(driverVehicleExisting.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle license plate not found for the driver's CPF provided.");
			}
			
			DriverVehicle driverVehicle = driverVehicleDto.toDriverVehicle();
			
			DriverVehicle driverVehicleUpdated = driverVehicleExisting.get();
			
			driverVehicleUpdated.setId(driverVehiclePrimaryKey);
			
			Optional<Driver> optionalDriver = driverRepository.findById(driverVehicle.getDriver().getCpfDriver());
			
			if(optionalDriver.isEmpty()) {
				return ResponseEntity.badRequest().body("Driver informed to update driver's vehicle not found.");
			}
			
			driverVehicleUpdated.setDriver(optionalDriver.get());
			driverVehicleUpdated.setDriverVehicleBrand(driverVehicle.getDriverVehicleBrand());
			driverVehicleUpdated.setDriverVehicleModel(driverVehicle.getModelYearVehicleDriver());
			driverVehicleUpdated.setYearOfManufactureDriverVehicle(driverVehicle.getYearOfManufactureDriverVehicle());
			driverVehicleUpdated.setModelYearVehicleDriver(driverVehicle.getModelYearVehicleDriver());
			
			driverVehicleRepository.save(driverVehicleUpdated);
			return ResponseEntity.ok(driverVehicleUpdated);
		}
	}
	
	private <T> Map<Path, String> validate(T dto) {
		Set<ConstraintViolation<T>> violations = validator.validate(dto);
		Map<Path, String> violationsMap = violations.stream()
				.collect(Collectors.toMap(violation -> violation.getPropertyPath(), violation -> violation.getMessage()));
		return violationsMap;
	}
}
