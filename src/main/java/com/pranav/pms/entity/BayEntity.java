package com.pranav.pms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pranav.pms.dto.ParkingSize;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Builder
public class BayEntity {
	
	@Id
	private String id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private ParkingSpaceEntity parkingSpaceEntity;
	private ParkingSize parkingSize;
	private Boolean vacant;
	private String carRegistrationNumber;

}
