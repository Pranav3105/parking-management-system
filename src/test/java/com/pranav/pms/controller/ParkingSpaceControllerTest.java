package com.pranav.pms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranav.pms.ParkingManagementSystemApplication;
import com.pranav.pms.dto.ParkingSpaceDto;
import com.pranav.pms.service.impl.ParkingSpaceManagementServiceImpl;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ParkingManagementSystemApplication.class)
public class ParkingSpaceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ParkingSpaceManagementServiceImpl parkingSpaceManagementServiceImpl;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	void createParkingSpaceTest() throws JsonProcessingException, Exception {
		ParkingSpaceDto parkingSpaceDto = ParkingSpaceDto.builder().medium(5).city("BLR").build();
		when(parkingSpaceManagementServiceImpl.addParkingLot(any())).thenAnswer(inv -> inv.getArgument(0));
		mockMvc.perform(post("http://localhost:8080/parking").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(parkingSpaceDto))).andExpect(status().isOk());
	}
	
	@Test
	void getParkingStatus() throws JsonProcessingException, Exception {
		ParkingSpaceDto parkingSpaceDto = ParkingSpaceDto.builder().medium(5).city("BLR")
				.id(UUID.randomUUID().toString()).build();
		when(parkingSpaceManagementServiceImpl.getParkingSpaceDetails(any())).thenReturn(parkingSpaceDto);
		mockMvc.perform(get("http://localhost:8080/parking/123")).andExpect(status().isOk());
	}

}
