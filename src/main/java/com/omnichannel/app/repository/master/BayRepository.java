package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.Bay;

@Repository
public interface BayRepository extends JpaRepository<Bay, Integer> {
	
	Boolean existsByBayNo(String name);
	
	Bay findByBayNo(String name);

}
