package com.pranav.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pranav.pms.dto.BayDto;
import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.service.ParkingAllocationService;

@RestController
public class AllocationController {
	
	@Autowired
	ParkingAllocationService parkingAllocationService;
	
	@PostMapping("/getslot/{parkingLotId}/{size}")
	public ResponseEntity<BayDto> allocateSpace(@PathVariable("parkingLotId") String parkingLotId, @PathVariable("size") ParkingSize parkingSize, @RequestParam("carNumber") String carNumber){
		BayDto bayDto = parkingAllocationService.allocateParkingSpace(parkingLotId, parkingSize, carNumber);
		return ResponseEntity.ok(bayDto);
	}
	
	@PostMapping("/releaseslot/{parkingLotId}/{slotid}")
	public ResponseEntity<BayDto> releaseSpace(@PathVariable("parkingLotId") String parkingLotId, @PathVariable("slotid") String slotId){
		BayDto bayDto = parkingAllocationService.releaseParkingSpace(parkingLotId, slotId);
		return ResponseEntity.ok(bayDto);
	}
}
