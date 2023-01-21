package com.omnichannel.app.repository.usermanagement;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.usermanagement.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
	
	Optional<Privilege> findByName(String name);

}
