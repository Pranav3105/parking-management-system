package com.pranav.pms.controller.handler;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pranav.pms.dto.ExceptionDto;
import com.pranav.pms.exception.ParkingManagementSystemException;

@RestControllerAdvice
public class ParkingManagementExceptionHandler {
	
	@ExceptionHandler(ParkingManagementSystemException.class)
	public ResponseEntity<ExceptionDto> handleParkingManagementException(ParkingManagementSystemException exception){
		ExceptionDto exceptionDto = new ExceptionDto();
		exceptionDto.setErrorMessage(exception.getMessage());
		exceptionDto.setErrorCode(exception.getErrorCode());
		exceptionDto.setTimeStamp(new Date().toString());
		return ResponseEntity.internalServerError().body(exceptionDto);
	}
}
