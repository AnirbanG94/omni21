package com.omnichannel.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.product.Packtype;

@Repository
public interface PacktypeRepository extends JpaRepository<Packtype, Integer>{

	boolean existsByPacktypename(String s);
	Packtype findByPacktypename(String s);
}
