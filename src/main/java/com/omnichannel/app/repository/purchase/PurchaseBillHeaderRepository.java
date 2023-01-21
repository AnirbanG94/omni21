package com.omnichannel.app.repository.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.purchase.PurchaseBillHeader;

@Repository	
public interface PurchaseBillHeaderRepository extends JpaRepository<PurchaseBillHeader, Integer> {
	
	@Query("SELECT u FROM PurchaseBillHeader u WHERE u.billDt BETWEEN ?1 AND ?2")
	List<PurchaseBillHeader> findBillByDate(String fromDate, String toDate);

}
