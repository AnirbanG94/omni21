package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.OutletDocDetails;

@Repository
public interface OutletDocDetailsRepository extends JpaRepository<OutletDocDetails, Integer> {

}
