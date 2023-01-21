package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.TotArticle;

@Repository
public interface TotArticleRepository extends JpaRepository<TotArticle, Integer> {

}
