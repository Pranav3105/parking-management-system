package com.pranav.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.dto.ParkingSpaceDto;
import com.pranav.pms.entity.BayEntity;
import com.pranav.pms.entity.ParkingSpaceEntity;
import com.pranav.pms.exception.ExceptionConstants;
import com.pranav.pms.exception.ParkingManagementSystemException;
import com.pranav.pms.repository.BayRepository;
import com.pranav.pms.repository.ParkingSpaceRepository;
import com.pranav.pms.service.ParkingSpaceManagementService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingSpaceManagementServiceImpl implements ParkingSpaceManagementService {
	
	@Autowired
	ParkingSpaceRepository parkingSpaceRepository;
	
	@Autowired
	BayRepository bayRepository;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public ParkingSpaceDto addParkingLot(ParkingSpaceDto parkingSpaceDto) {
		ParkingSpaceEntity parkingSpaceEntity = objectMapper.convertValue(parkingSpaceDto, ParkingSpaceEntity.class);
		parkingSpaceEntity.setId(UUID.randomUUID().toString());
		createParkingBays(parkingSpaceEntity);
		ParkingSpaceEntity savedParkingSpaceEntity = parkingSpaceRepository.save(parkingSpaceEntity);
		log.debug("Created parking space : {} with total slots : {}", savedParkingSpaceEntity.getId(),
				savedParkingSpaceEntity.getSmall() + savedParkingSpaceEntity.getMedium() +
				savedParkingSpaceEntity.getLarge() + savedParkingSpaceEntity.getExtraLarge());
		return objectMapper.convertValue(savedParkingSpaceEntity, ParkingSpaceDto.class);
	}
	
	private void createParkingBays(ParkingSpaceEntity parkingSpaceEntity) {
		List<BayEntity> bays = new ArrayList<>();
		int small = parkingSpaceEntity.getSmall();
		for (int i = 0; i < small; i++) {
			BayEntity bayEntity = BayEntity.builder().vacant(Boolean.TRUE).id(UUID.randomUUID().toString())
					.parkingSize(ParkingSize.SMALL).parkingSpaceEntity(parkingSpaceEntity).build();
			bays.add(bayEntity);
		}
		int medium = parkingSpaceEntity.getMedium();
		for (int i = 0; i < medium; i++) {
			BayEntity bayEntity = BayEntity.builder().vacant(Boolean.TRUE).id(UUID.randomUUID().toString())
					.parkingSize(ParkingSize.MEDIUM).parkingSpaceEntity(parkingSpaceEntity).build();
			bays.add(bayEntity);
		}
		
		int large = parkingSpaceEntity.getLarge();
		for (int i = 0; i < large; i++) {
			BayEntity bayEntity = BayEntity.builder().vacant(Boolean.TRUE).id(UUID.randomUUID().toString())
					.parkingSize(ParkingSize.LARGE).parkingSpaceEntity(parkingSpaceEntity).build();
			bays.add(bayEntity);
		}
		
		int extraLarge = parkingSpaceEntity.getLarge();
		for (int i = 0; i < extraLarge; i++) {
			BayEntity bayEntity = BayEntity.builder().vacant(Boolean.TRUE).id(UUID.randomUUID().toString())
					.parkingSize(ParkingSize.EXTRALARGE).parkingSpaceEntity(parkingSpaceEntity).build();
			bays.add(bayEntity);
		}
		parkingSpaceEntity.setBays(bays);
	}

	@Override
	public List<BayEntity> getFreeParkingSpaceBySizeAndId(ParkingSize size, String id) {
		return bayRepository.findByParkingSpaceEntityIdAndParkingSizeAndVacant(id, size, Boolean.TRUE);
	}

	@Override
	public ParkingSpaceDto getParkingSpaceDetails(String parkingSpaceId) {
		Optional<ParkingSpaceEntity> parkingSpaceEntityOp = parkingSpaceRepository.findById(parkingSpaceId);
		if(parkingSpaceEntityOp.isEmpty()) {
			throw new ParkingManagementSystemException("PMS_004", ExceptionConstants.PMS_004);
		}
		return objectMapper.convertValue(parkingSpaceEntityOp.get(), ParkingSpaceDto.class);
	}

}
