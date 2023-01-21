package com.omnichannel.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.product.ArticleStatus;

@Repository
public interface ArticleStatusRepository extends JpaRepository<ArticleStatus, Integer> {

}
