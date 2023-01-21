package com.omnichannel.app.repository.vendor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.vendor.VendorRegistreation;

@Repository
public interface VendorRegistreationRepository extends JpaRepository<VendorRegistreation, Integer> {

	Boolean existsByUsername(String username);

	Boolean existsByEmail1(String email);

	List<VendorRegistreation> findByInvitee(Integer invitee);

}
