package com.omnichannel.app.repository.usermanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.usermanagement.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
	
	List<Role> findByName(String name);

}
