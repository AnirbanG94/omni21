package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.asset.AssetBookingHeader;
import com.omnichannel.app.model.asset.AssetExecution;
import com.omnichannel.app.model.asset.AssetRegistration;
import com.omnichannel.app.model.product.Articles;

public interface AssetService {

    // ============================= ASSET Registration Start==================

    public List<AssetRegistration> getAssetRegistration();

    public ResponseEntity<String> postAssetRegistration(@Valid AssetRegistration assetRegistration);

    public AssetRegistration getAssetRegistrationById(Integer id);

    public ResponseEntity<String> updateAssetRegistration(@Valid AssetRegistration assetRegistration);

    public ResponseEntity<String> deleteAssetRegistration(Integer id);

    public List<AssetRegistration> getAssetByOutletType(String type);

    // ============================= ASSET Registration End==================

    // ================================== Asset Booking Start ===================

    public ResponseEntity<?> getUserForAssetBooking();

    public AssetBookingHeader getAssetBookingId(Integer id);

    public List<AssetBookingHeader> getAssetBooking();

    public ResponseEntity<String> postAssetBooking(AssetBookingHeader assetBookingHeader);

    public ResponseEntity<String> updateAssetBooking(@Valid AssetBookingHeader assetBookingHeader);

    public ResponseEntity<String> deleteAssetBookingDetails(Integer id);

    public List<Articles> getArticleForAssetBookingByVendorId(String family, Integer vendorId);

    // ================================== Asset Booking End ===================

    // ============================= Asset Booking Approval Start==================

    public List<AssetBookingHeader> getAssetsVisibilityBookingForApprove();

    public ResponseEntity<String> updateAssetBookingApproval(Integer id, Double discount);

    public ResponseEntity<String> updateAssetBookingDisapprove(Integer id, String reason);

    // ============================= Asset Booking Approval End==================

    // ============================= Asset Execution Start==================

    public List<AssetExecution> getAssetExecution();

    public ResponseEntity<String> postAssetExecution(@Valid AssetExecution assetExecution);

    public AssetExecution getAssetExecutionById(Integer id);

    public ResponseEntity<String> updateAssetExecution(AssetExecution assetExecution);

    public ResponseEntity<String> deleteAssetExecution(Integer id);

    public List<AssetBookingHeader> getAssetsBookingByVendorIdForExecution(Integer id);

    // ============================= Asset Execution End==================

    public String getAssetGST();

}
