package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.omnichannel.app.model.master.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
	
	Boolean existsByRegion(String region);
 	
	Region findByRegion(String region);

}
