package com.pranav.pms.service;

import com.pranav.pms.dto.BayDto;
import com.pranav.pms.dto.ParkingSize;

public interface ParkingAllocationService {
	
	public BayDto allocateParkingSpace(String parkingLotId, ParkingSize size, String carNumber);

	public BayDto releaseParkingSpace(String parkingLotId, String slotId);

}
