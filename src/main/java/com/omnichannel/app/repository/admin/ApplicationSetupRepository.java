package com.omnichannel.app.repository.admin;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.ApplicationSetup;

@Repository
public interface ApplicationSetupRepository extends JpaRepository<ApplicationSetup, Integer> {
	
	Boolean existsByKey(String key);
	
	Optional<ApplicationSetup> findByKey(String key);

	

}
