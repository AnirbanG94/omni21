package com.omnichannel.app.controller.vendor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.DTO.VendorManufacturerDTO;
import com.omnichannel.app.model.DTO.POBILL.POProduct;
import com.omnichannel.app.model.product.Manufacturer;
import com.omnichannel.app.model.vendor.ShippingNotification;
import com.omnichannel.app.model.vendor.VendorInvitation;
import com.omnichannel.app.model.vendor.VendorProductDetails;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.model.vendor.VendorRegistreation;
import com.omnichannel.app.service.VendorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    VendorService vendorService;

    // ============================= Vendor Invitation Start==================
    @Operation(summary = "API TO FETCH VENDOR INVITATION")
    @GetMapping(value = "/getVendorInvitation")
    public List<VendorInvitation> getVendorInvitation() {
        return vendorService.getVendorInvitation();
    }

    @Operation(summary = "API TO FETCH VENDOR INVITATION BY ID")
    @GetMapping(value = "/getVendorInvitationById")
    public VendorInvitation getVendorInvitationById(@RequestParam Integer id) {
        return vendorService.getVendorInvitationById(id);
    }

    @Operation(summary = "API TO SENT VENDOR INVITATION")
    @PostMapping(value = "/postVendorInvitation", consumes = "application/json")
    public ResponseEntity<String> postVendorInvitation(@Valid @RequestBody VendorInvitation vendorInvitation) {
        return vendorService.postVendorInvitation(vendorInvitation);
    }

    @Operation(summary = "API TO UPDATE VENDOR INVITATION")
    @PutMapping(value = "/updateVendorInvitation", consumes = "application/json")
    public ResponseEntity<String> updateVendorInvitation(@Valid @RequestBody VendorInvitation vendorInvitation) {
        return vendorService.updateVendorInvitation(vendorInvitation);
    }
    // ============================= Vendor Invitation End==================

    // ============================= Vendor Registration Start==================
    @Operation(summary = "API TO FETCH VENDOR FOR REGISTRATION")
    @GetMapping(value = "/getVendorRegistration")
    public List<VendorRegistreation> getVendorRegistration() {
        return vendorService.getVendorRegistration();
    }
    
    @Operation(summary = "API TO FETCH APPROVED Vendors")
    @GetMapping(value = "/getApprovedVendors")
    public List<VendorRegistreation> getApprovedVendors() {
        return vendorService.getApprovedVendors();
    }

    @Operation(summary = "API TO FETCH VENDOR FOR Vendor")
    @GetMapping(value = "/getVendorForVendor")
    public VendorRegistreation getVendorForVendor() {
        return vendorService.getVendorForVendor();
    }

    @Operation(summary = "API FOR VENDOR REGISTRATION")
    @PostMapping(value = "/postVendorRegistration", consumes = "application/json")
    public ResponseEntity<String> postVendorRegistration(@Valid @RequestBody VendorRegistreation vendorRegistreation) {
        return vendorService.postVendorRegistration(vendorRegistreation);
    }

    @Operation(summary = "API FOR VENDOR REGISTRATION FOR ONBOARDING")
    @PostMapping(value = "/postVendorRegistrationOnboarding", consumes = "application/json")
    public ResponseEntity<String> postVendorRegistrationOnboarding(
            @RequestBody VendorRegistreation vendorRegistreation) {
        return vendorService.postVendorRegistrationOnboarding(vendorRegistreation);
    }

    @Operation(summary = "API FOR GET VENDOR BY ID")
    @GetMapping(value = "/getVendorRegistrationById")
    public VendorRegistreation getVendorRegistrationById(@RequestParam Integer id) {
        return vendorService.getVendorRegistrationById(id);
    }

    @Operation(summary = "API FOR VENDOR REGISTRATION UPDATATION")
    @PutMapping(value = "/updateVendorRegistration", consumes = "application/json")
    public ResponseEntity<String> updateVendorRegistration(
            @Valid @RequestBody VendorRegistreation vendorRegistreation) {
        return vendorService.updateVendorRegistration(vendorRegistreation);
    }

    @Operation(summary = "API FOR VENDOR DEACTIVATION")
    @DeleteMapping(value = "/deleteVendorRegistration", produces = "application/json")
    public ResponseEntity<String> deleteVendorRegistration(@RequestParam Integer id) {
        return vendorService.deleteVendorRegistration(id);
    }
    
    // ============================= Vendor Registration End==================

    // ============================= Vendor Approval Start==================
    @Operation(summary = "API TO FETCH ALL VENDOR FOR APPROVAL")
    @GetMapping(value = "/getVendorsForApprove")
    public List<VendorRegistreation> getVendorsForApprove() {
        return vendorService.getVendorsForApprove();
    }

    @Operation(summary = "API FOR VENDOR APPROVAL")
    @PutMapping(value = "/updateVendorApproval", consumes = "application/json")
    public ResponseEntity<String> updateVendorApproval(@RequestParam Integer id) {
        return vendorService.updateVendorApproval(id);
    }

    @Operation(summary = "API FOR VENDOR DISAPPROVAL")
    @PutMapping(value = "/updateVendorDisapprove", consumes = "application/json")
    public ResponseEntity<String> updateVendorDisapprove(@RequestParam Integer id, @RequestParam String reason) {
        return vendorService.updateVendorDisapprove(id, reason);
    }

    // ============================= Vendor Approval End==================

    // ============================= Vendor Product Start==================

    @GetMapping(value = "/getVendorForProduct")
    public List<VendorRegistreation> getVendorForProduct() {
        return vendorService.getVendorForProduct();
    }

    @GetMapping(value = "/getVendorProductId")
    public VendorProductHeader getVendorProductId(@RequestParam Integer id) {
        return vendorService.getVendorProductId(id);
    }

    @GetMapping(value = "/getVendorProduct")
    public List<VendorProductHeader> getVendorProduct() {
        return vendorService.getVendorProduct();
    }

    @GetMapping(value = "/getVendorForProductInBulk")
    public List<VendorProductHeader> getVendorForProductInBulk(@RequestParam Integer[] id) {
        return vendorService.getVendorForProductInBulk(id);
    }

    @PostMapping(value = "/postVendorProduct", consumes = "application/json")
    public ResponseEntity<String> postVendorProduct(@Valid @RequestBody VendorProductHeader vendorProductHeader) {
        return vendorService.postVendorProduct(vendorProductHeader);
    }

    @PutMapping(value = "/updateVendorProduct", consumes = "application/json")
    public ResponseEntity<String> updateVendorProduct(@Valid @RequestBody VendorProductHeader vendorProductHeader) {
        return vendorService.updateVendorProduct(vendorProductHeader);
    }

    @DeleteMapping(value = "/deleteVendorProduct")
    public ResponseEntity<String> deleteVendorProduct(@RequestParam Integer id) {
        return vendorService.deleteVendorProduct(id);
    }

    @Operation(summary = "API TO FETCH ALL VENDOR PRODUCT BY VENDOR ID ")
    @GetMapping(value = "/getVendorProductByVendorId")
    public List<VendorProductHeader> getVendorProductByVendorId(@RequestParam Integer id) {
        return vendorService.getVendorProductByVendorId(id);
    }

    @Operation(summary = "API TO FETCH ALL VENDOR APPROVE PRODUCT BY VENDOR ID ")
    @GetMapping(value = "/getVendorFinalProductByVendorId")
    public List<VendorProductHeader> getVendorFinalProductByVendorId(@RequestParam Integer id) {
        return vendorService.getVendorFinalProductByVendorId(id);
    }

    @Operation(summary = "API TO FETCH ALL VENDOR APPROVE PRODUCT BY VENDOR ID FOR PO ")
    @GetMapping(value = "/getVendorFinalProductByVendorIdForPo")
    public List<POProduct> getVendorFinalProductByVendorIdForPo(@RequestParam Integer id) {
        return vendorService.getVendorFinalProductByVendorIdForPo(id);
    }

    @Operation(summary = "API TO FETCH PRICE DETAILS FOR PRODICT ")
    @GetMapping(value = "/getProductCostDeatils")
    public VendorProductDetails getProductCostDeatils(@RequestParam Integer id) {
        return vendorService.getProductCostDeatils(id);
    }

    // ============================= Vendor Product End==================

    // ============================= Vendor Product Approval Start==================
    @Operation(summary = "API TO FETCH ALL VENDOR PRODUCT FOR APPROVAL")
    @GetMapping(value = "/getVendorsProductForApprove")
    public List<VendorProductHeader> getVendorsProductForApprove() {
        return vendorService.getVendorsProductForApprove();
    }

    @Operation(summary = "API FOR VENDOR PRODUCT APPROVAL")
    @PutMapping(value = "/updateVendorProductApproval", consumes = "application/json")
    public ResponseEntity<String> updateVendorProductApproval(@RequestParam Integer id) {
        return vendorService.updateVendorProductApproval(id);
    }

    @Operation(summary = "API FOR VENDOR PRODUCT DISAPPROVAL")
    @PutMapping(value = "/updateVendorProductDisapprove", consumes = "application/json")
    public ResponseEntity<String> updateVendorProductDisapprove(@RequestParam Integer id, @RequestParam String reason) {
        return vendorService.updateVendorProductDisapprove(id, reason);
    }

    // ============================= Vendor Product Approval End==================

    // ============================= ShippingNotification Start==================

    @Operation(summary = "API Shipping Notification Data Fetch")
    @GetMapping(value = "/getShippingNotification")
    public List<ShippingNotification> getShippingNotification() {
        return vendorService.getShippingNotification();
    }

    @Operation(summary = "API Shipping Notification Data Fetch By Id")
    @GetMapping(value = "/getShippingNotificationByid")
    public ShippingNotification getShippingNotificationByid(@RequestParam Integer id) {
        return vendorService.getShippingNotificationByid(id);
    }

    @Operation(summary = "API Shipping Notification Data Save")
    @PostMapping(value = "/addShippingNotification", consumes = "application/json")
    public ResponseEntity<String> addShippingNotification(
            @Valid @RequestBody ShippingNotification shippingNotification) {
        return vendorService.addShippingNotification(shippingNotification);
    }

    @Operation(summary = "API Shipping Notification Data Modify")
    @PutMapping(value = "/updateShippingNotification", consumes = "application/json")
    public ResponseEntity<String> updateShippingNotification(
            @Valid @RequestBody ShippingNotification shippingNotification) {
        return vendorService.updateShippingNotification(shippingNotification);
    }

    @Operation(summary = "API Shipping Notification Data Deleted")
    @DeleteMapping(value = "/deleteShippingNotification")
    public ResponseEntity<String> deleteShippingNotification(@RequestParam Integer id) {
        return vendorService.deleteShippingNotification(id);
    }

    // ============================= ShippingNotification End==================

    // ============================= GKM Start==================

    @Operation(summary = "API GKM Data Fetch")
    @GetMapping(value = "/getGKMData")
    public List<VendorProductHeader> getGKMData() {
        return vendorService.getGKMData();
    }

    // ============================= GKM End==================

    // ============================= Vendor Manufacturer Start==================
    
    @Operation(summary = "API TO Fatch Vendor Manufacturer Data")
    @GetMapping(value = "/getVenManufData")
    public VendorManufacturerDTO getVenManufData(@RequestParam Integer id) {
        return vendorService.getVenManufData(id);
    }
    
    @PostMapping(value = "/postVenManufMapping", consumes = "application/json")
    public ResponseEntity<String> postVenManufMapping(@RequestBody VendorManufacturerDTO vendorManufacturerDTO) {
        return vendorService.postVenManufMapping(vendorManufacturerDTO);
    }
    
    @Operation(summary = "API TO FETCH Manufacturer By Vendors ID")
    @GetMapping(value = "/getManufactureByVendorId")
    public List<Manufacturer> getManufactureByVendorId(@RequestParam Integer id) {
        return vendorService.getManufactureByVendorId(id);
    }
    

    // ============================= Vendor Manufacturer End==================
    
    // ============================= Vendor replacement==================
    
    @Operation(summary = "API For Vendor Replacement")
    @PostMapping(value = "/vendorReplacement")
    public ResponseEntity<String> vendorReplacement(@RequestParam Integer oldId,@RequestParam Integer newId) {
        System.out.println(oldId);
        System.out.println(newId);
        return vendorService.vendorReplacement(oldId,newId);
    }
    
    // ============================= Vendor replacement==================

}
