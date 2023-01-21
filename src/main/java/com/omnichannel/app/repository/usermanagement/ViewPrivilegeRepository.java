package com.omnichannel.app.repository.usermanagement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.usermanagement.ViewPrivilege;

@Repository
public interface ViewPrivilegeRepository extends JpaRepository<ViewPrivilege, Integer>{
	
	Optional<ViewPrivilege> findBySubmenu(String submenu);
	
	List<ViewPrivilege> findByMenu(String menu);

}
