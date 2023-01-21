package com.omnichannel.app.controller.asset;

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

import com.omnichannel.app.model.asset.AssetBookingHeader;
import com.omnichannel.app.model.asset.AssetExecution;
import com.omnichannel.app.model.asset.AssetRegistration;
import com.omnichannel.app.model.product.Articles;
import com.omnichannel.app.service.AssetService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    AssetService assetService;

    // ============================= ASSET Registration Start==================

    @Operation(summary = "API TO FETCH ASSET FOR REGISTRATION")
    @GetMapping(value = "/getAssetRegistration")
    public List<AssetRegistration> getAssetRegistration() {
        return assetService.getAssetRegistration();
    }

    @Operation(summary = "API FOR ASSET REGISTRATION")
    @PostMapping(value = "/postAssetRegistration", consumes = "application/json")
    public ResponseEntity<String> postAssetRegistration(@Valid @RequestBody AssetRegistration assetRegistration) {
        return assetService.postAssetRegistration(assetRegistration);
    }

    @Operation(summary = "API FOR GET ASSET BY ID")
    @GetMapping(value = "/getAssetRegistrationById")
    public AssetRegistration getAssetRegistrationById(@RequestParam Integer id) {
        return assetService.getAssetRegistrationById(id);
    }

    @Operation(summary = "API FOR ASSET REGISTRATION UPDATATION")
    @PutMapping(value = "/updateAssetRegistration", consumes = "application/json")
    public ResponseEntity<String> updateAssetRegistration(@Valid @RequestBody AssetRegistration assetRegistration) {
        return assetService.updateAssetRegistration(assetRegistration);
    }

    @Operation(summary = "API FOR ASSET Deleted")
    @DeleteMapping(value = "/deleteAssetRegistration", produces = "application/json")
    public ResponseEntity<String> deleteAssetRegistration(@RequestParam Integer id) {
        return assetService.deleteAssetRegistration(id);
    }

    @GetMapping(value = "/getAssetByOutletType")
    public List<AssetRegistration> getAssetBookingType(@RequestParam String type) {
        return assetService.getAssetByOutletType(type);
    }

    // ============================= ASSET Registration End==================

    // ================================== Asset Booking Start ===================

    @GetMapping(value = "/getUserForAssetBooking")
    public ResponseEntity<?> getUserForAssetBooking() {
        return assetService.getUserForAssetBooking();
    }

    @GetMapping(value = "/getAssetBookingId")
    public AssetBookingHeader getAssetBookingId(@RequestParam Integer id) {
        return assetService.getAssetBookingId(id);
    }

    @GetMapping(value = "/getAssetBooking")
    public List<AssetBookingHeader> getAssetBooking() {
        return assetService.getAssetBooking();
    }

    @Operation(summary = "API FOR AssetsVisibility BOOKING")
    @PostMapping(value = "/postAssetBooking", consumes = "application/json")
    public ResponseEntity<String> postAssetBooking(@RequestBody AssetBookingHeader assetBookingHeader) {
        return assetService.postAssetBooking(assetBookingHeader);
    }

    @PutMapping(value = "/updateAssetBooking", consumes = "application/json")
    public ResponseEntity<String> updateAssetBooking(@Valid @RequestBody AssetBookingHeader assetBookingHeader) {
        return assetService.updateAssetBooking(assetBookingHeader);
    }

    @DeleteMapping(value = "/deleteAssetBookingDetails")
    public ResponseEntity<String> deleteAssetBookingDetails(@RequestParam Integer id) {
        return assetService.deleteAssetBookingDetails(id);
    }

    @GetMapping(value = "/getArticleForAssetBookingByVendorId")
    public List<Articles> getArticleForAssetBookingByVendorId(@RequestParam String family,
            @RequestParam Integer vendorId) {
        return assetService.getArticleForAssetBookingByVendorId(family, vendorId);
    }

    // ================================== Asset Booking End ===================

    // ============================= Asset Booking Approval Start==================

    @Operation(summary = "API TO FETCH ALL  FOR APPROVAL")
    @GetMapping(value = "/getAssetsVisibilityBookingForApprove")
    public List<AssetBookingHeader> getAssetBookingForApprove() {
        return assetService.getAssetsVisibilityBookingForApprove();
    }

    @Operation(summary = "API FOR ASSET BOOKING APPROVAL")
    @PutMapping(value = "/updateAssetBookingApproval", consumes = "application/json")
    public ResponseEntity<String> updateAssetBookingApproval(@RequestParam Integer id, @RequestParam Double discount) {
        return assetService.updateAssetBookingApproval(id, discount);
    }

    @Operation(summary = "API FOR ASSET BOOKING DISAPPROVAL")
    @PutMapping(value = "/updateAssetBookingDisapprove", consumes = "application/json")
    public ResponseEntity<String> updateAssetBookingDisapprove(@RequestParam Integer id, @RequestParam String reason) {
        return assetService.updateAssetBookingDisapprove(id, reason);
    }

    // ============================= Asset Booking Approval End==================

    // ============================= Asset Execution Start==================

    @Operation(summary = "API TO FETCH Asset FOR EXECUTION")
    @GetMapping(value = "/getAssetExecution")
    public List<AssetExecution> getAssetExecution() {
        return assetService.getAssetExecution();
    }

    @Operation(summary = "API FOR Asset EXECUTION")
    @PostMapping(value = "/postAssetExecution", consumes = "application/json")
    public ResponseEntity<String> postAssetExecution(@Valid @RequestBody AssetExecution assetExecution) {
        return assetService.postAssetExecution(assetExecution);
    }

    @Operation(summary = "API FOR GET Asset EXECUTION BY ID")
    @GetMapping(value = "/getAssetExecutionById")
    public AssetExecution getAssetExecutionById(@RequestParam Integer id) {
        return assetService.getAssetExecutionById(id);
    }

    @Operation(summary = "API FOR Asset EXECUTION UPDATATION")
    @PutMapping(value = "/updateAssetExecution", consumes = "application/json")
    public ResponseEntity<String> updateAssetExecution(@RequestBody AssetExecution assetExecution) {
        return assetService.updateAssetExecution(assetExecution);
    }

    @Operation(summary = "API FOR Asset EXECUTION Deleted")
    @DeleteMapping(value = "/deleteAssetExecution", produces = "application/json")
    public ResponseEntity<String> deleteAssetExecution(@RequestParam Integer id) {
        return assetService.deleteAssetExecution(id);
    }

    @Operation(summary = "API TO FETCH ALL VENDOR APPROVE BOOKING BY VENDOR ID FOR EXECUTION ")
    @GetMapping(value = "/getAssetsBookingByVendorIdForExecution")
    public List<AssetBookingHeader> getAssetsBookingByVendorIdForExecution(@RequestParam Integer id) {
        return assetService.getAssetsBookingByVendorIdForExecution(id);
    }

    // ============================= Asset Execution End==================

    @Operation(summary = "API TO FETCH ASSET GST DATA")
    @GetMapping(value = "/getAssetGST")
    public String getAssetGST() {
        return assetService.getAssetGST();
    }
}
