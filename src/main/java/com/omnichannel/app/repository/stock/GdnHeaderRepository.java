package com.omnichannel.app.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.GdnHeader;

@Repository
public interface GdnHeaderRepository extends JpaRepository<GdnHeader, Integer> {

    List<GdnHeader> findByActive(Boolean active);

    List<GdnHeader> findByPoId(Integer poId);

}
