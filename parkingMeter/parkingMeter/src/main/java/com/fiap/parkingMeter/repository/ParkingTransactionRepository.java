package com.fiap.parkingMeter.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.domain.ParkingTransactionPrimaryKey;

public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, ParkingTransactionPrimaryKey> {
	
	@Query(value =
            "select ifnull(max(parking_control_sequence_number), 0)" +
            "from parking_control " +
            "where parking_identifier_code = :parking_identifier_code" +
            "  and driver_cpf = :driver_cpf" +
            "  and vehicle_driver_license_plate = :vehicle_driver_license_plate", nativeQuery = true)
    int getMaxParkingTransactionSequenceNumber(
            @Param("parking_identifier_code") int parkingIdentifierCode,
            @Param("driver_cpf") String driverCpf,
            @Param("vehicle_driver_license_plate") String vehicleDriverLicensePlate);
    

    @Query("SELECT pc FROM ParkingTransaction pc WHERE pc.parkingEndTime IS NULL " +
            "AND pc.parkingStartTime <= :targetTime " +
            "AND TIMESTAMPADD(SECOND, 3600, pc.parkingStartTime) >= :targetTime")
    List<ParkingTransaction> findParkingControlsAboutToExpire(@Param("targetTime") LocalDateTime targetTime);
}