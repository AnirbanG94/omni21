package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.AssetsType;

@Repository
public interface AssetsTypeRepository extends JpaRepository<AssetsType, Integer> {
	
	Boolean existsByType(String typr);
 	
	AssetsType findByType(String typr);

}
