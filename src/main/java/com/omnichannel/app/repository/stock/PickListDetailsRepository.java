package com.omnichannel.app.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.PickListDetails;

@Repository
public interface PickListDetailsRepository extends JpaRepository<PickListDetails, Integer> {

}
