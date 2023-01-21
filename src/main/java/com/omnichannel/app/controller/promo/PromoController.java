package com.omnichannel.app.controller.promo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.DTO.PromoArticle;
import com.omnichannel.app.model.promo.GeneralPromo;
import com.omnichannel.app.model.promo.Promo;
import com.omnichannel.app.service.PromoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PromoController {

    @Autowired
    PromoService promoService;

    // ============================= Vendor Promo Start==================

    @Operation(summary = "API For Promo Data Fetch For Vendor")
    @GetMapping(value = "/getPromoForVendor")
    public List<Promo> getPromoForVendor() {
        return promoService.getPromoForVendor();
    }

    @Operation(summary = "API For Promo Data Fetch For Customer")
    @GetMapping(value = "/getPromoForCustomer")
    public List<Promo> getPromoForCustomer() {
        return promoService.getPromoForCustomer();
    }

    @Operation(summary = "API Promo Data Fetch By Id")
    @GetMapping(value = "/getPromoByid")
    public Promo getPromoByid(@RequestParam Integer id) {
        return promoService.getPromoByid(id);
    }

    @Operation(summary = "API Promo Data Save For Vendor")
    @PostMapping(value = "/postPromoForVendor", consumes = "application/json")
    public ResponseEntity<String> postPromoForVendor(@Valid @RequestBody Promo promo) {
        return promoService.postPromoForVendor(promo);
    }

    @Operation(summary = "API Promo Data Save For Customer")
    @PostMapping(value = "/postPromoForCustomer", consumes = "application/json")
    public ResponseEntity<String> postPromoForCustomer(@Valid @RequestBody Promo promo) {
        return promoService.postPromoForCustomer(promo);
    }

    @Operation(summary = "API Promo Data Modify For Vendor")
    @PutMapping(value = "/updatePromoForVendor", consumes = "application/json")
    public ResponseEntity<String> updatePromoForVendor(@Valid @RequestBody Promo promo) {
        return promoService.updatePromoForVendor(promo);
    }

    @Operation(summary = "API Promo Data Modify For Customer")
    @PutMapping(value = "/updatePromoForCustomer", consumes = "application/json")
    public ResponseEntity<String> updatePromoForCustomer(@Valid @RequestBody Promo promo) {
        return promoService.updatePromoForCustomer(promo);
    }

    @Operation(summary = "API Promo Data Deleted")
    @DeleteMapping(value = "/deletePromo")
    public ResponseEntity<String> deletePromo(@RequestParam Integer id) {
        return promoService.deletePromo(id);
    }
    
    @Operation(summary = "API TO Get Article Details For Promo")
    @GetMapping(value = "/getArticleDetailsForPromo")
    public PromoArticle getArticleDetailsForPromo(@RequestParam Integer id) {
        return promoService.getArticleDetailsForPromo(id);
    }

    // ============================= Vendor Promo End==================

    // =============================Vendor Promo Approval Start==================

    @Operation(summary = "API For Vendor Promo Data Fetch For Approval")
    @GetMapping(value = "/getVendorPromoForApproval")
    public List<Promo> getVendorPromoForApproval() {
        return promoService.getVendorPromoForApproval();
    }

    @Operation(summary = "API For Vendor Promo Approval")
    @PutMapping(value = "/promoVendorApproval", consumes = "application/json")
    public ResponseEntity<String> promoVendorApproval(@RequestParam Integer id) {
        return promoService.promoVendorApproval(id);
    }

    @Operation(summary = "API For Vendor Promo Disapproval")
    @PutMapping(value = "/promoVendorDisapproval", consumes = "application/json")
    public ResponseEntity<String> promoVendorDisapproval(@RequestParam Integer id, @RequestParam String reason) {
        return promoService.promoVendorDisapproval(id, reason);
    }

    // ============================= Vendor Promo Approval End==================

    // =============================Customer Promo Approval Start==================

    @Operation(summary = "API For Customer Promo Data Fetch For Approval")
    @GetMapping(value = "/getCustomerPromoForApproval")
    public List<Promo> getCustomerPromoForApproval() {
        return promoService.getCustomerPromoForApproval();
    }

    @Operation(summary = "API For Customer Promo Approval")
    @PutMapping(value = "/promoCustomerApproval", consumes = "application/json")
    public ResponseEntity<String> promoCustomerApproval(@RequestParam Integer id) {
        return promoService.promoCustomerApproval(id);
    }

    @Operation(summary = "API For Customer Promo Disapproval")
    @PutMapping(value = "/promoCustomerDisapproval", consumes = "application/json")
    public ResponseEntity<String> promoCustomerDisapproval(@RequestParam Integer id, @RequestParam String reason) {
        return promoService.promoCustomerDisapproval(id, reason);
    }

    // ============================= Customer Promo Approval End==================

    // ============================= General Promo Start==================

    @Operation(summary = "API General Promo Data Fetch")
    @GetMapping(value = "/getGeneralPromo")
    public List<GeneralPromo> getGeneralPromo() {
        return promoService.getGeneralPromo();
    }

    @Operation(summary = "API General Promo Data Fetch By Id")
    @GetMapping(value = "/getGeneralPromoByid")
    public GeneralPromo getGeneralPromoByid(@RequestParam Integer id) {
        return promoService.getGeneralPromoByid(id);
    }

    @Operation(summary = "API General Promo Data Save")
    @PostMapping(value = "/postGeneralPromo", consumes = "application/json")
    public ResponseEntity<String> postGeneralPromo(@Valid @RequestBody GeneralPromo generalPromo) {
        return promoService.postGeneralPromo(generalPromo);
    }

    @Operation(summary = "API General Promo Data Modify")
    @PutMapping(value = "/updateGeneralPromo", consumes = "application/json")
    public ResponseEntity<String> updateGeneralPromo(@Valid @RequestBody GeneralPromo generalPromo) {
        return promoService.updateGeneralPromo(generalPromo);
    }

    @Operation(summary = "API General Promo Data Deleted")
    @DeleteMapping(value = "/deleteGeneralPromo")
    public ResponseEntity<String> deleteGeneralPromo(@RequestParam Integer id) {
        return promoService.deleteGeneralPromo(id);
    }

    // ============================= General Promo End==================

}
