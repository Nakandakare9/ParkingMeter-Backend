package com.fiap.parkingMeter.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fiap.parkingMeter.service.ValidateTimeService;

import lombok.Getter;

@Service
public class ValidateTimeServiceImpl implements ValidateTimeService {

	private LocalDateTime timeStampStartParkingControl;
	@Getter
	private boolean alertSent;

	@Override
	public void startValidation(LocalDateTime timeStampStart) {
		this.timeStampStartParkingControl = timeStampStart;
		this.alertSent = false;
	}

	@Scheduled(fixedRate = 60000) // Running every minute
	public void validateParking() {
		if (timeStampStartParkingControl == null) {
			return;
		}

		LocalDateTime now = LocalDateTime.now();
		Duration parkingDuration = Duration.between(timeStampStartParkingControl, now);
		long minutesParking = parkingDuration.toMinutes();

		if (minutesParking >= 60) {
			long minutesRemaining = minutesParking % 60;

			if (minutesRemaining >= 55 && !alertSent) {
				System.out.println("Warning: 5 minutes left for every hour parked.");
				alertSent = true;
			} else if (minutesRemaining < 55) {
				alertSent = false;
			}
		}

	}

}
