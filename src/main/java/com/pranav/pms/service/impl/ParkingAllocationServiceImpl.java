package com.pranav.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.pms.dto.BayDto;
import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.entity.BayEntity;
import com.pranav.pms.exception.ExceptionConstants;
import com.pranav.pms.exception.ParkingManagementSystemException;
import com.pranav.pms.repository.BayRepository;
import com.pranav.pms.repository.ParkingSpaceRepository;
import com.pranav.pms.service.ParkingAllocationService;
import com.pranav.pms.service.ParkingSpaceManagementService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingAllocationServiceImpl implements ParkingAllocationService {
	
	@Autowired
	ParkingSpaceManagementService parkingSpaceManagementService;
	
	@Autowired
	BayRepository bayRepository;
	
	@Autowired
	ParkingSpaceRepository parkingSpaceRepository;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BayDto allocateParkingSpace(String parkingLotId, ParkingSize size, String carNumber) {
		log.debug("Trying to allocate parking space for {} in parking lot {}",carNumber, parkingLotId);
		List<BayEntity> emptyBays = new ArrayList<>();
		while(emptyBays.isEmpty() && size!=null) {
			emptyBays = parkingSpaceManagementService.getFreeParkingSpaceBySizeAndId(size, parkingLotId);
			size = getNextParkingSize(size);
		}
		if(!emptyBays.isEmpty()) {
			BayEntity bayEntity = emptyBays.stream().findAny().get();
			bayEntity.setCarRegistrationNumber(null);
			bayEntity.setVacant(Boolean.FALSE);
			bayEntity.setCarRegistrationNumber(carNumber);
			bayRepository.save(bayEntity);
			log.debug("Allocated space for {} in the bay {}",carNumber, bayEntity.getId());
			return objectMapper.convertValue(bayEntity, BayDto.class);
		}
		log.error("Failed to allocate space for {}", carNumber);
		throw new ParkingManagementSystemException("PMS_002", ExceptionConstants.PMS_002);
	}
	
	private ParkingSize getNextParkingSize(ParkingSize currentParkingSize) {
		if(currentParkingSize.equals(ParkingSize.SMALL))
			return ParkingSize.MEDIUM;
		else if(currentParkingSize.equals(ParkingSize.MEDIUM))
			return ParkingSize.LARGE;
		else if(currentParkingSize.equals(ParkingSize.LARGE))
			return ParkingSize.EXTRALARGE;
		else
			return null;
	}

	@Override
	public BayDto releaseParkingSpace(String parkingLotId, String slotId) {
		log.debug("Releasing space {} {}", parkingLotId, slotId);
		Optional<BayEntity> bayEntity = bayRepository.findById(slotId);
		if(bayEntity.isEmpty() || !bayEntity.get().getParkingSpaceEntity().getId().equals(parkingLotId) || bayEntity.get().getVacant().equals(Boolean.TRUE)) {
			log.error("No Parking lot assigned slot id : {} ", slotId);
			throw new ParkingManagementSystemException("PMS_003", ExceptionConstants.PMS_003);
		}
		BayEntity bayEntityData = bayEntity.get();

		log.info("Release space {} assigned to car {} ", slotId, bayEntityData.getCarRegistrationNumber());
		
		bayEntityData.setCarRegistrationNumber(null);
		
		bayEntityData.setVacant(Boolean.TRUE);
		
		BayEntity savedBayEntity = bayRepository.save(bayEntityData);
		
		return objectMapper.convertValue(savedBayEntity, BayDto.class);
	}

}
