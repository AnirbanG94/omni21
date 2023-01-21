package com.omnichannel.app.repository.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.asset.AssetBookingDetails;

@Repository
public interface AssetBookingDetailsRepository extends JpaRepository<AssetBookingDetails, Integer> {

}
