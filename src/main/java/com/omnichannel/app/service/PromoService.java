package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.omnichannel.app.model.DTO.PromoArticle;
import com.omnichannel.app.model.promo.GeneralPromo;
import com.omnichannel.app.model.promo.Promo;

@Service
public interface PromoService {

    // ============================= Promo Start==================

    public List<Promo> getPromoForVendor();

    public List<Promo> getPromoForCustomer();

    public Promo getPromoByid(Integer id);

    public ResponseEntity<String> postPromoForVendor(@Valid Promo promo);

    public ResponseEntity<String> postPromoForCustomer(@Valid Promo promo);

    public ResponseEntity<String> updatePromoForVendor(@Valid Promo promo);

    public ResponseEntity<String> updatePromoForCustomer(@Valid Promo promo);

    public ResponseEntity<String> deletePromo(Integer id);
    
    public PromoArticle getArticleDetailsForPromo(Integer id);

    // ============================= Promo End==================

    // =============================Vendor Promo Approval Start==================

    public List<Promo> getVendorPromoForApproval();

    public ResponseEntity<String> promoVendorApproval(Integer id);

    public ResponseEntity<String> promoVendorDisapproval(Integer id, String reason);

    // =============================Vendor Promo Approval end==================

    // =============================Customer Promo Approval Start==================

    public List<Promo> getCustomerPromoForApproval();

    public ResponseEntity<String> promoCustomerApproval(Integer id);

    public ResponseEntity<String> promoCustomerDisapproval(Integer id, String reason);

    // =============================Customer Promo Approval end==================

    // ============================= General Promo Start==================
    public List<GeneralPromo> getGeneralPromo();

    public GeneralPromo getGeneralPromoByid(Integer id);

    public ResponseEntity<String> postGeneralPromo(@Valid GeneralPromo generalPromo);

    public ResponseEntity<String> updateGeneralPromo(@Valid GeneralPromo generalPromo);

    public ResponseEntity<String> deleteGeneralPromo(Integer id);



    // ============================= General Promo End==================

}
