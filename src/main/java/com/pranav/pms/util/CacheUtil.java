package com.pranav.pms.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.entity.BayEntity;
import com.pranav.pms.service.ParkingSpaceManagementService;

@Component
public class CacheUtil {
	
	@Autowired
	ParkingSpaceManagementService parkingSpaceManagementService;
	
	@CachePut(cacheNames = "parkingBays", key = "#id + #size.toString()")
	public List<BayEntity> updateFreeBayEntity(String id, ParkingSize size, String status, BayEntity bayEntity){
		List<BayEntity> emptyBays = parkingSpaceManagementService.getFreeParkingSpaceBySizeAndId(size, id);
		Map<String, BayEntity> bayMap = emptyBays.stream().collect(Collectors.toMap(BayEntity::getId, Function.identity()));
		if (status.equals("allocate")) {
			bayMap.remove(bayEntity.getId());
		} else if (status.equals("release")) {
			bayMap.put(bayEntity.getId(), bayEntity);
		}
		List<BayEntity> modifiedBays =  bayMap.values().parallelStream().collect(Collectors.toList());
		return modifiedBays;
	}

}
