package com.omnichannel.app.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.Tot;

@Repository
public interface TotRepository extends JpaRepository<Tot, Integer> {
	/*
	 * @Query(
	 * value="select m.cost_all,m.cost_sp_item,m.gkm_all,m.gkm_sp_item from tot_master m"
	 * ,nativeQuery = true) public List<Tot>findMasterValidFor();
	 */
}
