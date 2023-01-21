package com.omnichannel.app.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.OpeningStock;

@Repository
public interface OpeningStockRepository extends JpaRepository<OpeningStock,Integer> {

    List<OpeningStock> findByActive(Boolean active);
    
}
