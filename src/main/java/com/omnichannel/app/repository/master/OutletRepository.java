package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.Outlet;

@Repository
public interface OutletRepository extends JpaRepository<Outlet, Integer> {
	
	Boolean existsByName(String Name);
 	
	Outlet findByName(String Name);

}
