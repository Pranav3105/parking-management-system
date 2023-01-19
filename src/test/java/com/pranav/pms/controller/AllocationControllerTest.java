package com.pranav.pms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pranav.pms.ParkingManagementSystemApplication;
import com.pranav.pms.dto.BayDto;
import com.pranav.pms.exception.ParkingManagementSystemException;
import com.pranav.pms.service.impl.ParkingAllocationServiceImpl;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = ParkingManagementSystemApplication.class)
public class AllocationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ParkingAllocationServiceImpl parkingAllocationServiceImpl;
	
	@Test
	void allocationTest() throws Exception {
		BayDto bayDto = BayDto.builder().id(UUID.randomUUID().toString()).vacant(false).carRegistrationNumber("abcde").build();
		when(parkingAllocationServiceImpl.allocateParkingSpace(any(), any(), any())).thenReturn(bayDto);
		mockMvc.perform(post("http://localhost:8080/getslot/123/SMALL").queryParam("carNumber", "123")).andExpect(status().isOk());
	}
	
	@Test
	void allocationFailureTest() throws Exception {
		when(parkingAllocationServiceImpl.allocateParkingSpace(any(), any(), any())).thenThrow(ParkingManagementSystemException.class);
		mockMvc.perform(post("http://localhost:8080/getslot/123/SMALL").queryParam("carNumber", "123")).andExpect(status().isInternalServerError());
	}
	
	@Test
	void ReleaseTest() throws Exception {
		BayDto bayDto = BayDto.builder().id(UUID.randomUUID().toString()).vacant(true).carRegistrationNumber("abcde").build();
		when(parkingAllocationServiceImpl.releaseParkingSpace(any(), any())).thenReturn(bayDto);
		mockMvc.perform(post("http://localhost:8080/releaseslot/123/123")).andExpect(status().isOk());
	}
	
	@Test
	void releaseFailureTest() throws Exception {
		when(parkingAllocationServiceImpl.releaseParkingSpace(any(), any())).thenThrow(ParkingManagementSystemException.class);
		mockMvc.perform(post("http://localhost:8080/releaseslot/123/123").queryParam("carNumber", "123")).andExpect(status().isInternalServerError());
	}

}
