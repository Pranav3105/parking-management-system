package com.pranav.pms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceDto {
	
	private String id;
	private String parkingName;
	private String city;
	@Builder.Default
	private Integer small = 0;
	@Builder.Default
	private Integer medium = 0;
	@Builder.Default
	private Integer large = 0;
	@Builder.Default
	private Integer extraLarge = 0;
	@JsonManagedReference
	private List<BayDto> bays;

}
