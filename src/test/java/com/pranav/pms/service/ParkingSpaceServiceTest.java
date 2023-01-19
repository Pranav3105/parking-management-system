package com.pranav.pms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.pms.dto.ParkingSpaceDto;
import com.pranav.pms.entity.ParkingSpaceEntity;
import com.pranav.pms.exception.ParkingManagementSystemException;
import com.pranav.pms.repository.ParkingSpaceRepository;
import com.pranav.pms.service.impl.ParkingSpaceManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParkingSpaceServiceTest {
	
	@InjectMocks
	ParkingSpaceManagementServiceImpl parkingSpaceManagementServiceImpl;
	
	@Mock
	ParkingSpaceRepository parkingSpaceRepository;
	
	ParkingSpaceEntity parkingEntity = null;
	
	ParkingSpaceDto parkingDto = null;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	public void dataSetup() {
		parkingDto = ParkingSpaceDto.builder().city("BLR").small(5).medium(5).large(5).extraLarge(5).build();
		parkingEntity = objectMapper.convertValue(parkingDto, ParkingSpaceEntity.class);
	}
	
	@Test
	public void createParkingSpaceTest() {
		when(parkingSpaceRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
		
		ParkingSpaceDto savedParkingSpaceDto = parkingSpaceManagementServiceImpl.addParkingLot(parkingDto);
		
		assertEquals(20, savedParkingSpaceDto.getSmall()+savedParkingSpaceDto.getMedium()+savedParkingSpaceDto.getLarge()+savedParkingSpaceDto.getExtraLarge());
	}
	
	@Test
	public void getParkingStatusTest() {
		
		when(parkingSpaceRepository.findById(any())).thenReturn(Optional.of(parkingEntity));
		
		ParkingSpaceDto savedParkingSpaceDto = parkingSpaceManagementServiceImpl.getParkingSpaceDetails("12345");
		
		assertNotNull(savedParkingSpaceDto);
	}
	
	@Test
	public void getParkingStatusInvalidTest() {
		
		when(parkingSpaceRepository.findById(any())).thenReturn(Optional.empty());
		
		assertThrows(ParkingManagementSystemException.class, () -> parkingSpaceManagementServiceImpl.getParkingSpaceDetails("12345"));
	}

}
