/**
 * 
 */
package com.omnichannel.app.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.purchase.PurchaseBillDetails;

/**
 * @author Rajsekhar Acharya
 *
 */
@Repository
public interface PurchaseBillDetailsRepository extends JpaRepository<PurchaseBillDetails, java.lang.Integer> {

}
