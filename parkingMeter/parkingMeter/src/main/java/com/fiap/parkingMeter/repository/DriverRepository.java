package com.fiap.parkingMeter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.parkingMeter.domain.Driver;
import com.fiap.parkingMeter.domain.DriverPrimaryKey;

public interface DriverRepository extends JpaRepository<Driver, DriverPrimaryKey>{

}
