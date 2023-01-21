package com.omnichannel.app.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.purchase.PurchaseTerms;

@Repository
public interface PurchaseTermsRepository extends JpaRepository<PurchaseTerms, Integer> {
	
	Boolean existsByTerms(String terms);
 	
	PurchaseTerms findByTerms(String terms);

}
