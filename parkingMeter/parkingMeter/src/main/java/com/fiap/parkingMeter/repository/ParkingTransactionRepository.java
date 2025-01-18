package com.fiap.parkingMeter.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.domain.ParkingTransactionPrimaryKey;

public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, ParkingTransactionPrimaryKey> {

	@Query(value = "select ifnull(max(parking_transaction_sequence_number), 0)" + "from parking_transaction "
			+ "where parking_identifier_code = :parking_identifier_code" + "  and cpf_driver = :cpf_driver"
			+ "  and driver_vehicle_license_plate = :driver_vehicle_license_plate", nativeQuery = true)
	int getMaxParkingTransactionSequenceNumber(@Param("parking_identifier_code") int parkingIdentifierCode,
			@Param("cpf_driver") String driverCpf,
			@Param("driver_vehicle_license_plate") String vehicleDriverLicensePlate);

	@Query("SELECT pt FROM ParkingTransaction pt WHERE pt.parkingEndTime IS NULL "
			+ "AND pt.parkingStartTime <= :targetTime "
			+ "AND TIMESTAMPADD(SECOND, 3600, pt.parkingStartTime) >= :targetTime")
	List<ParkingTransaction> findParkingControlsAboutToExpire(@Param("targetTime") LocalDateTime targetTime);
}