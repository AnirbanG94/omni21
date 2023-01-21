package com.omnichannel.app.repository.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
 	Boolean existsByName(String name);
 	
 	Optional<Company> findByName(String name);

}
