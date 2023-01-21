package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.omnichannel.app.model.master.Transportation;

@Repository
public interface TransportationRepositorty extends JpaRepository<Transportation, Integer> {
	
	Boolean existsByName(String Name);
 	
	Transportation findByName(String Name);

}
