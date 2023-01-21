package com.omnichannel.app.repository.vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.vendor.ShippingNotification;

@Repository
public interface ShippingNotificationRepository extends JpaRepository<ShippingNotification, Integer> {

}
