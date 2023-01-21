package com.omnichannel.app.repository.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.GrnHeader;

@Repository
public interface GrnHeaderRepository extends JpaRepository<GrnHeader, Integer> {

    List<GrnHeader> findByActive(Boolean active);
    List<GrnHeader> findByPoId(Integer poId);

}
