package com.pranav.pms.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BayDto {
	
	private String id;
	private ParkingSize parkingSize;
	private Boolean vacant;
	private String carRegistrationNumber;
	@JsonBackReference
	private ParkingSpaceDto parkingSpaceDto;
}
