package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.omnichannel.app.model.master.Paymentmode;

@Repository
public interface PaymentmodeRepository extends JpaRepository<Paymentmode, Integer> {
	
	Boolean existsByPaymentmode(String paymentmode);
 	
	Paymentmode findByPaymentmode(String paymentmode);

}
