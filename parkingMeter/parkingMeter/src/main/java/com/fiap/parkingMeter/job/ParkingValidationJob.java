package com.fiap.parkingMeter.job;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiap.parkingMeter.domain.ParkingTransaction;
import com.fiap.parkingMeter.repository.ParkingTransactionRepository;

@Component
public class ParkingValidationJob {
	private final ParkingTransactionRepository parkingTransactionRepository;
	
	public ParkingValidationJob(ParkingTransactionRepository parkingTransactionRepository) {
        this.parkingTransactionRepository = parkingTransactionRepository;
    }
	
	//@Scheduled(fixedDelay = 60000) //Checando a cada 60 segundos
	@Scheduled(fixedDelay = 3000) //Para teste, checando a cada 3 segundos
    public void validateParking() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Checking parking");
        
        List<ParkingTransaction> parkingsExpire = parkingTransactionRepository.findParkingControlsAboutToExpire(now);
        
        for (ParkingTransaction parkingTransaction : parkingsExpire) {
            //LocalDateTime expectedExpire = parkingTransaction.getParkingStartTime().plusHours(1); //Definição de duração para o parquimetro de 1 hora
        	LocalDateTime expectedExpire = parkingTransaction.getParkingStartTime().plusMinutes(5); //Para teste, com duração para o estacionamento de apenas 5 minutos
            if (now.plusMinutes(5).isAfter(expectedExpire)) {
                sendAlert(parkingTransaction);
            }
        }
	}
    
	//Simula o envio para o serviço de Minutaria para notificar o piloto
    private void sendAlert(ParkingTransaction parkingTransaction) {
    	System.out.println("--------------------------WARNING--------------------------");
    	System.out.println("Warning: 5 minutes left before completing each parked hour.");
        System.out.println("Vehicle: " + parkingTransaction.getDriverVehicle().getDriverVehicleModel());
        System.out.println("Driver: " + parkingTransaction.getDriverVehicle().getDriver().getNameDriver());
        System.out.println("-----------------------------------------------------------");
    }
        
        

}