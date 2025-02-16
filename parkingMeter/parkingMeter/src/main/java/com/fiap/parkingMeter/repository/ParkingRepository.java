package com.fiap.parkingMeter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fiap.parkingMeter.domain.Parking;
import com.fiap.parkingMeter.domain.ParkingPrimaryKey;

public interface ParkingRepository extends JpaRepository<Parking, ParkingPrimaryKey> {

	@Query(value = "SELECT IFNULL(MAX(parking_identifier_code), 0) FROM parking", nativeQuery = true)
    int getMaxParkingId();
}