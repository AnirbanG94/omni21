package com.omnichannel.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.omnichannel.app.model.product.UOM;

@Repository
public interface UOMRepository extends JpaRepository<UOM, Integer> {
	
	Boolean existsByName(String name);
	
	UOM findByName(String name);

}
