package com.pranav.pms.service;

import java.util.List;

import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.dto.ParkingSpaceDto;
import com.pranav.pms.entity.BayEntity;

public interface ParkingSpaceManagementService {
	
	public ParkingSpaceDto addParkingLot(ParkingSpaceDto parkingSpaceDto);
	
	public ParkingSpaceDto getParkingSpaceDetails(String parkingSpaceId);
	
	public List<BayEntity> getFreeParkingSpaceBySizeAndId(ParkingSize size, String id);
}
