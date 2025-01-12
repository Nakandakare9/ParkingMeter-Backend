package com.fiap.parkingMeter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.parkingMeter.domain.DriverPaymentMethod;
import com.fiap.parkingMeter.domain.DriverPaymentMethodPrimaryKey;

public interface DriverPaymentMethodRepository extends JpaRepository<DriverPaymentMethod, DriverPaymentMethodPrimaryKey> {
	@Query(value= "select ifnull(max(sequential_number_payment_method), 0)" +
			"from driver_payment_method where cpf_driver = :cpf_driver", nativeQuery = true)
	int getMaxNumberSequentialDriver(@Param("cpf_driver") String cpfDriver);
	
	@Query(value= "select * from driver_payment_method where cpf_driver = :cpf_driver ", nativeQuery = true)
	List<DriverPaymentMethod> findDriverPaymentMethodByCpfDriver(@Param("cpf_driver") String cpfDriver);
	
	@Query(value= "select payment_method_type_code " +
			"from driver_payment_method " +
				"where cpf_driver = :cpf_driver " +
				" and selected_payment_method_indicator = 'S'", nativeQuery = true)
	int getTypePaymentMethodSelectedDriver(@Param("cpf_driver") String cpfDriver);
}
