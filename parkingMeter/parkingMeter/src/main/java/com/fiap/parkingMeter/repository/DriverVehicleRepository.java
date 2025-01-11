package com.fiap.parkingMeter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.parkingMeter.domain.DriverVehicle;
import com.fiap.parkingMeter.domain.DriverVehiclePrimaryKey;

public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, DriverVehiclePrimaryKey> {
	@Query(value= "select * from driver_vehicle where cpf_driver = :cpf_driver ", nativeQuery = true)
	List<DriverVehicle> findDriverVehicleByCpfDriver(@Param("cpf_driver") String cpfDriver);
	
	@Query(value= "select * from driver_vehicle where driver_vehicle_license_plate = :driver_vehicle_license_plate ", nativeQuery = true)
	List<DriverVehicle> findDriverVehicleByLicensePlate(@Param("driver_vehicle_license_plate") String driverVehicleLicensePlate);
}
