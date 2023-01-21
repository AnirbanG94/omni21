package com.omnichannel.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.product.PurchaseGroup;

@Repository
public interface PurchaseGroupRepository extends JpaRepository<PurchaseGroup, Integer> {
	
	Boolean existsByName(String name);
	
	PurchaseGroup findByName(String name);

}
