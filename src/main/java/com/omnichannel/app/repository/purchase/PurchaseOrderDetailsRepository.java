package com.omnichannel.app.repository.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.purchase.PurchaseOrderDetails;

@Repository
public interface PurchaseOrderDetailsRepository extends JpaRepository<PurchaseOrderDetails, Integer> {

    @Query(value = "CALL grn_gdnStat(:p_po_no);", nativeQuery = true)
    List<PurchaseOrderDetails> getPODetails(Integer p_po_no);

}
