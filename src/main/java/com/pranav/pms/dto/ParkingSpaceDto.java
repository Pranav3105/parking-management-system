package com.pranav.pms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class ParkingSpaceDto {
	
	private String id;
	private String parkingName;
	private String city;
	private Integer small;
	private Integer medium;
	private Integer large;
	private Integer extraLarge;
	@JsonManagedReference
	private List<BayDto> bays;

}
