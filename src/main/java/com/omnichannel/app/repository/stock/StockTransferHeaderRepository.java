package com.omnichannel.app.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.StockTransferHeader;

@Repository
public interface StockTransferHeaderRepository extends JpaRepository<StockTransferHeader,Integer> {

    List<StockTransferHeader> findByTransferFlagAndStatus(Boolean transferFlag,Boolean status);

    List<StockTransferHeader> findByTransferToIdAndTransferFlagAndStatus(Integer transferToId,Boolean transferFlag,Boolean status);
    
}
