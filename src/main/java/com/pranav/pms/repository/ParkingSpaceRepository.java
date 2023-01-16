package com.pranav.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pranav.pms.entity.ParkingSpaceEntity;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, String> {
	

}
