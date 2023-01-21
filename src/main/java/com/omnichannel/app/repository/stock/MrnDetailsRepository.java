package com.omnichannel.app.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.MrnDetails;

@Repository
public interface MrnDetailsRepository extends JpaRepository<MrnDetails, Integer> {
    
}
