package com.omnichannel.app.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.product.Articles;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Integer> {
	
	Boolean existsByName(String name);
	
	Articles findByName(String name);
	
    @Query(value = "select a.* from pro_article a join ven_product b on(a.article_id=b.article_id) join pro_item c on (a.product_id=c.id) where b.vendor_id = ?1 and c.pro_family = ?2", nativeQuery = true)
    public List<Articles> articleForAsser(Integer vendorId,String family);

}
