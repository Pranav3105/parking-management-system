package com.pranav.pms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pranav.pms.dto.ParkingSize;
import com.pranav.pms.entity.BayEntity;

@Repository
public interface BayRepository extends JpaRepository<BayEntity, String> {

	List<BayEntity> findByParkingSpaceEntityIdAndParkingSizeAndVacant(String id, ParkingSize size, Boolean isFree);
}
