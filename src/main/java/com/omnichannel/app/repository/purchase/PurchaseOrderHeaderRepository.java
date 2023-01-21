package com.omnichannel.app.repository.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.purchase.PurchaseOrderHeader;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeader, Integer> {

	@Query("SELECT u FROM PurchaseOrderHeader u WHERE u.poDt BETWEEN ?1 AND ?2")
	List<PurchaseOrderHeader> findPOByDate(String fromDate, String toDate);

	List<PurchaseOrderHeader> findByOutletIdAndStatusAndActive(Integer outletId, String status, Boolean active);

	@Query("SELECT u FROM PurchaseOrderHeader u WHERE u.vendorId = ?1 AND u.active=true AND u.status='A'")
	List<PurchaseOrderHeader> findPOByVendorId(Integer id);

	List<PurchaseOrderHeader> findByActive(Boolean active);

	List<PurchaseOrderHeader> findByGrnFlagAndOutletIdAndStatusAndActive(Boolean grnFlag, Integer outletId,
			String status, Boolean active);

	List<PurchaseOrderHeader> findByGdnFlagAndOutletIdAndStatusAndActive(Boolean gdnFlag, Integer outletId,
			String status, Boolean active);

	@Query("SELECT u FROM PurchaseOrderHeader u WHERE u.status !='A' AND u.status !='D' AND u.active=true")
	List<PurchaseOrderHeader> findForApproval();

	@Query("SELECT u FROM PurchaseOrderHeader u WHERE u.status =?1 AND u.active=true")
	List<PurchaseOrderHeader> findForLevelApproval(String level);

}
