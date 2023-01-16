package com.pranav.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pranav.pms.dto.ParkingSpaceDto;
import com.pranav.pms.service.ParkingSpaceManagementService;

@RestController
@RequestMapping(value = "/parking")
public class ParkingSpaceManagementController {

	@Autowired
	ParkingSpaceManagementService parkingSpaceManagementService;

	@PostMapping
	public ResponseEntity<ParkingSpaceDto> createNewParkingSpace(@RequestBody ParkingSpaceDto parkingSpaceDto) {
		return ResponseEntity.ok(parkingSpaceManagementService.addParkingLot(parkingSpaceDto));
	}

	@GetMapping("/{parkingLotId}")
	public ResponseEntity<ParkingSpaceDto> getCurrentParkingSpaceStatus(
			@PathVariable("parkingLotId") String parkingLotId) {
		return ResponseEntity.ok(parkingSpaceManagementService.getParkingSpaceDetails(parkingLotId));
	}

}
