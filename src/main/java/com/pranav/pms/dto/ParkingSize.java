package com.pranav.pms.dto;

public enum ParkingSize {

	SMALL("SMALL"), MEDIUM("MEDIUM"), LARGE("LARGE"), EXTRALARGE("EXTRALARGE");
	
	private final String parkingSize;
	
	private ParkingSize(String parkingSize) {
		this.parkingSize = parkingSize;
	}
	
	public String getParkingSize() {
		return this.parkingSize;
	}
}
