package com.pranav.pms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.pranav.pms.dto.BayDto;
import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.entity.BayEntity;
import com.pranav.pms.entity.ParkingSpaceEntity;
import com.pranav.pms.exception.ParkingManagementSystemException;
import com.pranav.pms.repository.BayRepository;
import com.pranav.pms.repository.ParkingSpaceRepository;
import com.pranav.pms.service.impl.ParkingAllocationServiceImpl;
import com.pranav.pms.service.impl.ParkingSpaceManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AllottmentServiceTest {
	
	@InjectMocks
	ParkingAllocationServiceImpl parkingAllocationServiceImpl;
	
	@Mock
	ParkingSpaceManagementServiceImpl parkingSpaceManagementServiceImpl;
	
	@Mock
	ParkingSpaceRepository parkingSpaceRepository;
	
	@Mock
	BayRepository bayRepository;
	
	@Test
	public void allocateParkingSpaceTest() {
		
		String parkingLotId = UUID.randomUUID().toString();
		
		ParkingSize parkingSizeSmall = ParkingSize.SMALL;
		
		ParkingSize parkingSizeMedium = ParkingSize.MEDIUM;
		
		ParkingSize parkingSizeLarge = ParkingSize.LARGE;
		
		ParkingSize parkingSizeExtraLarge = ParkingSize.EXTRALARGE;
		
		String carNumber = "KA10YU3456";
		
		BayEntity bayEntity = BayEntity.builder().id(UUID.randomUUID().toString()).parkingSize(parkingSizeMedium).build();
		
		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(parkingSizeSmall, parkingLotId)).thenReturn(Collections.emptyList());

		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(parkingSizeMedium, parkingLotId)).thenReturn(Collections.singletonList(bayEntity));
		
		BayDto bayDto = parkingAllocationServiceImpl.allocateParkingSpace(parkingLotId, parkingSizeSmall, carNumber);
		
		assertEquals(bayEntity.getId(), bayDto.getId());
		
		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(parkingSizeMedium, parkingLotId)).thenReturn(Collections.emptyList());

		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(parkingSizeLarge, parkingLotId)).thenReturn(Collections.singletonList(bayEntity));
		
		bayDto = parkingAllocationServiceImpl.allocateParkingSpace(parkingLotId, parkingSizeMedium, carNumber);
		
		assertEquals(bayEntity.getId(), bayDto.getId());
		
		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(parkingSizeLarge, parkingLotId)).thenReturn(Collections.emptyList());

		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(parkingSizeExtraLarge, parkingLotId)).thenReturn(Collections.singletonList(bayEntity));
		
		bayDto = parkingAllocationServiceImpl.allocateParkingSpace(parkingLotId, parkingSizeLarge, carNumber);
		
		assertEquals(bayEntity.getId(), bayDto.getId());
	}
	
	@Test
	public void allocateParkingFailureTest() {
		when(parkingSpaceManagementServiceImpl.getFreeParkingSpaceBySizeAndId(any(), any())).thenReturn(Collections.emptyList());
		assertThrows(ParkingManagementSystemException.class, ()->parkingAllocationServiceImpl.allocateParkingSpace("acde", ParkingSize.SMALL, ""));
	}
	
	@Test
	public void releaseParkingFailureTest() {
		when(bayRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(ParkingManagementSystemException.class, ()->parkingAllocationServiceImpl.releaseParkingSpace("acde",""));
	}
	
	@Test
	public void releaseParkingSpaceTest() {
		String parkingLotId = UUID.randomUUID().toString();
		
		String parkingSlotId = UUID.randomUUID().toString();
		
		ParkingSpaceEntity parkingSpaceEntity = new ParkingSpaceEntity();
		
		parkingSpaceEntity.setId(parkingLotId);
		
		BayEntity bayEntity = BayEntity.builder().id(parkingSlotId).carRegistrationNumber("abcde").parkingSize(ParkingSize.MEDIUM).vacant(Boolean.FALSE).parkingSpaceEntity(parkingSpaceEntity).build();
		
		when(bayRepository.findById(parkingSlotId)).thenReturn(Optional.of(bayEntity));
		
		when(bayRepository.save(any())).thenAnswer(inv->inv.getArgument(0));
		
		BayDto bayDto = parkingAllocationServiceImpl.releaseParkingSpace(parkingLotId, parkingSlotId);
		
		assertEquals(true, bayDto.getVacant());
		
	}
	
	

}
