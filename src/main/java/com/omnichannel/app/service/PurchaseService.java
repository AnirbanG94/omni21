package com.omnichannel.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.DTO.POBILL.POHeader;
import com.omnichannel.app.model.purchase.PurchaseBillHeader;
import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.model.purchase.PurchaseTerms;

public interface PurchaseService {

	// ============================= Purchase Order Start==================

	public List<PurchaseOrderHeader> getPurchaseOrder();

	public List<PurchaseOrderHeader> getDetailsPurchaseOrder();

	public List<PurchaseOrderHeader> getPurchaseOrderByMonth(String fromDate, String todate);

	public POHeader getPurchaseOrderByid(Integer id);

	public List<PurchaseOrderHeader> getPurchaseOrderByOutletId(Integer id);

	public List<PurchaseOrderHeader> getPurchaseOrderByVendorId(Integer id);

	public PurchaseOrderHeader getPurchaseOrderByidForView(Integer id);

	public PurchaseOrderHeader getPurchaseOrderByidForAsset(Integer id);

	public ResponseEntity<String> postPurchaseOrder(@Valid POHeader pOHeader);

	public ResponseEntity<String> updatePurchaseOrder(@Valid POHeader pOHeader);

	public ResponseEntity<String> deletePurchaseOrder(Integer id);

	// ============================= Purchase Order End==================

	// ============================= Purchase Order Approval Start==================
	public List<PurchaseOrderHeader> getPurchaseOrderForApproval();

	public ResponseEntity<String> approvePurchaseOrder(Integer id);

	public ResponseEntity<String> disapprovePurchaseOrder(Integer id, String reason);

	// ============================= Purchase Order Approval End==================

	// ============================= Purchase Bill Start==================

	public List<PurchaseBillHeader> getPurchaseBill();

	public List<PurchaseOrderHeader> getPurchaseOrderForPurchaseBill();

	public List<PurchaseBillHeader> getDetailsPurchaseBill();

	public List<PurchaseBillHeader> getPurchaseBillByMonth(String fromDate, String todate);

	public PurchaseBillHeader getPurchaseBillByid(Integer id);

	public PurchaseBillHeader getPurchaseBillByidForView(Integer id);

	public ResponseEntity<String> postPurchaseBill(@Valid POHeader pOHeader);

	public ResponseEntity<String> updatePurchaseBill(@Valid PurchaseBillHeader purchaseBillHeader);

	public ResponseEntity<String> deletePurchaseBill(Integer id);

	// ============================= Purchase Bill End==================

	// ============================= Purchase Terms Start==================

	public List<PurchaseTerms> getPurchaseTerms();

	public ResponseEntity<String> addPurchaseTerms(PurchaseTerms purchaseTerms);

	public ResponseEntity<String> updatePurchaseTerms(PurchaseTerms purchaseTerms);

	public ResponseEntity<String> deletePurchaseTerms(Integer id);

	public PurchaseTerms getPurchaseTermsByid(Integer id);

	// ============================= Purchase Terms End==================

}
