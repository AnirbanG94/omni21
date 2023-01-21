package com.omnichannel.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omnichannel.app.model.product.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
	
	Boolean existsByManufacname(String name);

	Manufacturer findByManufacname(String name);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE ven_manuf SET ven_id = ?1 WHERE ven_id= ?2", nativeQuery = true)
	void updateVenManuf(Integer newId, Integer oldId);

}
