package com.omnichannel.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.product.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
	
	Boolean existsByBrandName(String name);
	Brand findByBrandName(String name);

}
