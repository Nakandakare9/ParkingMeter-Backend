package com.fiap.parkingMeter.service;

import java.time.LocalDateTime;

public interface ValidateTimeService {

	void startValidation(LocalDateTime timeStampStart);
	void validateParking();
}
