package com.omnichannel.app.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.omnichannel.app.model.stock.GrnDetails;

public interface GrnDetailsRepository extends JpaRepository<GrnDetails, Integer> {

    @Query(value = "SELECT a.qty FROM grn_dtl a join grn_hdr b on (b.id= a.grn_hdr_id) WHERE b.po_id=?1 AND a.article_id=?2", nativeQuery = true)
    Double grnArticleQty(Integer poId, Integer articleId);

}
