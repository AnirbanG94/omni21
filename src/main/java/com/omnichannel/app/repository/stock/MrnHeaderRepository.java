package com.omnichannel.app.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.MrnHeader;

@Repository
public interface MrnHeaderRepository extends JpaRepository<MrnHeader, Integer> {

    List<MrnHeader> findByStatus(Boolean status);
    
}
