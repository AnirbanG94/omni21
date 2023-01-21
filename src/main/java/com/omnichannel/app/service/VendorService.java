package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import com.omnichannel.app.model.DTO.VendorManufacturerDTO;
import com.omnichannel.app.model.DTO.POBILL.POProduct;
import com.omnichannel.app.model.product.Manufacturer;
import com.omnichannel.app.model.vendor.ShippingNotification;
import com.omnichannel.app.model.vendor.VendorInvitation;
import com.omnichannel.app.model.vendor.VendorProductDetails;
import com.omnichannel.app.model.vendor.VendorProductHeader;
import com.omnichannel.app.model.vendor.VendorRegistreation;

public interface VendorService {

    // ============================= Vendor Invitation Start==================

    public List<VendorInvitation> getVendorInvitation();

    public VendorInvitation getVendorInvitationById(Integer id);

    public ResponseEntity<String> postVendorInvitation(@Valid VendorInvitation vendorInvitation);

    public ResponseEntity<String> updateVendorInvitation(@Valid VendorInvitation vendorInvitation);

    // ============================= Vendor Invitation End==================

    // ============================= Vendor Registration Start==================

    public List<VendorRegistreation> getVendorRegistration();

    public VendorRegistreation getVendorForVendor();

    public List<VendorRegistreation> getApprovedVendors();

    public ResponseEntity<String> postVendorRegistration(@Valid VendorRegistreation vendorRegistreation);

    public ResponseEntity<String> postVendorRegistrationOnboarding( VendorRegistreation vendorRegistreation);

    public VendorRegistreation getVendorRegistrationById(Integer id);

    public ResponseEntity<String> updateVendorRegistration(@Valid VendorRegistreation vendorRegistreation);

    public ResponseEntity<String> deleteVendorRegistration(Integer id);

    // ============================= Vendor Registration End==================

    // ============================= Vendor Approval Start==================

    public List<VendorRegistreation> getVendorsForApprove();

    public ResponseEntity<String> updateVendorApproval(Integer id);

    public ResponseEntity<String> updateVendorDisapprove(Integer id, String reason);

    // ============================= Vendor Approval End==================

    // ============================= Vendor Product Start==================

    public List<VendorRegistreation> getVendorForProduct();

    public VendorProductHeader getVendorProductId(Integer id);

    public List<VendorProductHeader> getVendorProduct();

    public List<VendorProductHeader> getVendorForProductInBulk(Integer[] id);

    public ResponseEntity<String> postVendorProduct(@Valid VendorProductHeader vendorProductHeader);

    public ResponseEntity<String> updateVendorProduct(@Valid VendorProductHeader vendorProductHeader);

    public ResponseEntity<String> deleteVendorProduct(Integer id);

    public List<VendorProductHeader> getVendorProductByVendorId(Integer id);

    public List<VendorProductHeader> getVendorFinalProductByVendorId(Integer id);

    public List<POProduct> getVendorFinalProductByVendorIdForPo(Integer id);

    public VendorProductDetails getProductCostDeatils(Integer id);

    // ============================= Vendor Product End==================

    // ============================= Vendor Product Approval Start==================

    public List<VendorProductHeader> getVendorsProductForApprove();

    public ResponseEntity<String> updateVendorProductApproval(Integer id);

    public ResponseEntity<String> updateVendorProductDisapprove(Integer id, String reason);

    // ============================= Vendor Product Approval End==================

    // ============================= ShippingNotification Start==================

    public List<ShippingNotification> getShippingNotification();

    public ShippingNotification getShippingNotificationByid(Integer id);

    public ResponseEntity<String> addShippingNotification(@Valid ShippingNotification shippingNotification);

    public ResponseEntity<String> updateShippingNotification(@Valid ShippingNotification shippingNotification);

    public ResponseEntity<String> deleteShippingNotification(Integer id);

    // ============================= ShippingNotification Start==================

    // ============================= GKM Start==================
    public List<VendorProductHeader> getGKMData();
    // ============================= GKM Start==================

    // ============================= Vendor Manufacturer Start==================
    public VendorManufacturerDTO getVenManufData(Integer id);

    public ResponseEntity<String> postVenManufMapping(VendorManufacturerDTO vendorManufacturerDTO);
    
    public List<Manufacturer> getManufactureByVendorId(Integer id);
    // ============================= Vendor Manufacturer End==================

	public ResponseEntity<String> vendorReplacement(Integer oldId, Integer newId);



}
