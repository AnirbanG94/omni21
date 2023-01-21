package com.omnichannel.app.repository.vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.vendor.VendorDocDetails;

@Repository
public interface VendorDocDetailsRepository extends JpaRepository<VendorDocDetails, Integer> {

}
