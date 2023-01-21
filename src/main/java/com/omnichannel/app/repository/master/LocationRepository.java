package com.omnichannel.app.repository.master;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	 	Boolean existsByNameAndStateId(String name,Integer stateId);
	 	
	 	Optional<Location> findByName(String name);

}
