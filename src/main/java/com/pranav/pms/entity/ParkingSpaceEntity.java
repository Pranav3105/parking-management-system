package com.pranav.pms.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class ParkingSpaceEntity {

	@Id
	private String id;
	private String parkingName;
	private String city;
	private Integer small;
	private Integer medium;
	private Integer large;
	private Integer extraLarge;
	@OneToMany(mappedBy = "parkingSpaceEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<BayEntity> bays;

}
