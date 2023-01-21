package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.TotHeader;

@Repository
public interface TotHeaderRepository extends JpaRepository<TotHeader, Integer> {
	/*
	 * @Query(
	 * value="select h.*,d.off_invoice,d.on_invoice,d.particulars,d.tot from tot_hdr h left join tot_dtl d on (h.dtl_id=d.id)"
	 * ,nativeQuery = true) public List<TotHeader>findTotDetail();
	 */
}
