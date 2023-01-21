package com.omnichannel.app.repository.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.stock.PickListHeader;

@Repository
public interface PickListHeaderRepository extends JpaRepository<PickListHeader, Integer> {

    List<PickListHeader> findByStatus(Boolean status);

    @Query(value = "SELECT a.asset_no FROM asset_reg a JOIN pro_item b ON (a.product_family=b.pro_family) JOIN ven_product c ON (c.item_id=b.id) WHERE a.status is true AND c.article_id=?1", nativeQuery = true)
    public List<String> getAssetNoForPickList(Integer articleId);

    public List<PickListHeader> findByFromIdAndStatus(Integer fromId,Boolean status);

}
