package com.omnichannel.app.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.TotDetail;

@Repository
public interface TotDetailRepository extends JpaRepository<TotDetail, Integer> {
  
	/*
	 * @Query(
	 * value="select d.* from tot_dtl d left join tot_hdr h on (h.dtl_id=d.id) where h.valid_for=?1"
	 * ,nativeQuery = true) public List<TotDetail>findTotDetail();
	 */
}
