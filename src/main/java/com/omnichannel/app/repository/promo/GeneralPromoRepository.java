package com.omnichannel.app.repository.promo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.promo.GeneralPromo;

@Repository
public interface GeneralPromoRepository extends JpaRepository<GeneralPromo, Integer> {

}
