package com.omnichannel.app.repository.asset;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.asset.AssetBookingHeader;

@Repository
public interface AssetBookingHeaderRepository extends JpaRepository<AssetBookingHeader, Integer> {

    List<AssetBookingHeader> findByVendorId(Integer vendorId);

    @Query("SELECT u FROM AssetBookingHeader u WHERE u.status !='A' AND u.status !='D' AND u.active=true")
    List<AssetBookingHeader> findForApproval();

    @Query("SELECT u FROM AssetBookingHeader u WHERE u.status =?1 AND u.active=true")
    List<AssetBookingHeader> findForLevelApproval(String level);

}
