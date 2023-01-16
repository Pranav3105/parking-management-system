package com.pranav.pms.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
public class BayDto {
	
	private String id;
	private ParkingSize parkingSize;
	private Boolean vacant;
	private String carRegistrationNumber;
	@JsonBackReference
	private ParkingSpaceDto parkingSpaceDto;
}
