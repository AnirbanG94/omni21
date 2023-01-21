package com.omnichannel.app.repository.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.IdentificationSetup;

@Repository
public interface IdentificationSetupRepository extends JpaRepository<IdentificationSetup, Integer> {
	
	Optional<IdentificationSetup> findByMenu(Integer menu);
	
	Optional<IdentificationSetup> findByMenuAndTradingVendor(Integer menu,Boolean tradingVendor);

}
