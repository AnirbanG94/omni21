package com.omnichannel.app.controller.purchase;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.DTO.POBILL.POHeader;
import com.omnichannel.app.model.purchase.PurchaseBillHeader;
import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.model.purchase.PurchaseTerms;
import com.omnichannel.app.service.PurchaseService;

import io.swagger.v3.oas.annotations.Operation;

@RestController("/api/stock")
public class PurchaseController {

	@Autowired
	PurchaseService purchaseService;

	// ============================= Purchase Order Start==================

	@Operation(summary = "API Deatils Purchase Order Fetch")
	@GetMapping(value = "/getDetailsPurchaseOrder")
	public List<PurchaseOrderHeader> getDetailsPurchaseOrder() {
		return purchaseService.getDetailsPurchaseOrder();
	}

	@Operation(summary = "API Purchase Order Fetch")
	@GetMapping(value = "/getPurchaseOrder")
	public List<PurchaseOrderHeader> getPurchaseOrder() {
		return purchaseService.getPurchaseOrder();
	}

	@Operation(summary = "API Purchase Order Fetch By Month")
	@GetMapping(value = "/getPurchaseOrderByMonth")
	public List<PurchaseOrderHeader> getPurchaseOrderByMonth(@RequestParam String fromDate,
			@RequestParam String todate) {
		return purchaseService.getPurchaseOrderByMonth(fromDate, todate);
	}

	@Operation(summary = "API Purchase Order Data Fetch By Id")
	@GetMapping(value = "/getPurchaseOrderByid")
	public POHeader getPurchaseOrderByid(@RequestParam Integer id) {
		return purchaseService.getPurchaseOrderByid(id);
	}

	@Operation(summary = "API Purchase Order Data Fetch By OutletId")
	@GetMapping(value = "/getPurchaseOrderByOutletId")
	public List<PurchaseOrderHeader> getPurchaseOrderByOutletId(@RequestParam Integer id) {
		return purchaseService.getPurchaseOrderByOutletId(id);
	}

	@Operation(summary = "API Purchase Order Data Fetch By vendorId")
	@GetMapping(value = "/getPurchaseOrderByVendorId")
	public List<PurchaseOrderHeader> getPurchaseOrderByVendorId(@RequestParam Integer id) {
		return purchaseService.getPurchaseOrderByVendorId(id);
	}

	@Operation(summary = "API Purchase Order Data Fetch By Id For View")
	@GetMapping(value = "/getPurchaseOrderByidForView")
	public PurchaseOrderHeader getPurchaseOrderByidForView(@RequestParam Integer id) {
		return purchaseService.getPurchaseOrderByidForView(id);
	}

	@Operation(summary = "API Purchase Order Data Fetch By Id For Asset")
	@GetMapping(value = "/getPurchaseOrderByidForAsset")
	public PurchaseOrderHeader getPurchaseOrderByidForAsset(@RequestParam Integer id) {
		return purchaseService.getPurchaseOrderByidForAsset(id);
	}

	@Operation(summary = "API Purchase Order Data Save")
	@PostMapping(value = "/postPurchaseOrder", consumes = "application/json")
	public ResponseEntity<String> postPurchaseOrder(@Valid @RequestBody POHeader pOHeader) {
		return purchaseService.postPurchaseOrder(pOHeader);
	}

	@Operation(summary = "API Purchase Order Data Modify")
	@PutMapping(value = "/updatePurchaseOrder", consumes = "application/json")
	public ResponseEntity<String> updatePurchaseOrder(@Valid @RequestBody POHeader pOHeader) {
		return purchaseService.updatePurchaseOrder(pOHeader);
	}

	@Operation(summary = "API Purchase Order Data Deleted")
	@DeleteMapping(value = "/deletePurchaseOrder")
	public ResponseEntity<String> deletePurchaseOrder(@RequestParam Integer id) {
		return purchaseService.deletePurchaseOrder(id);
	}

	// ============================= Purchase Order End==================

	// ============================= Purchase Order Approval Start==================

	@Operation(summary = "API To Fetch Purchase Order List For Approval")
	@GetMapping(value = "/getPurchaseOrderForApproval")
	public List<PurchaseOrderHeader> getPurchaseOrderForApproval() {
		return purchaseService.getPurchaseOrderForApproval();
	}

	@Operation(summary = "API Purchase Order Fetch for Purchase Bill")
	@GetMapping(value = "/getPurchaseOrderForPurchaseBill")
	public List<PurchaseOrderHeader> getPurchaseOrderForPurchaseBill() {
		return purchaseService.getPurchaseOrderForPurchaseBill();
	}

	@Operation(summary = "API For Purchase Order Approval")
	@PutMapping(value = "/approvePurchaseOrder", consumes = "application/json")
	public ResponseEntity<String> approvePurchaseOrder(@RequestParam Integer id) {
		return purchaseService.approvePurchaseOrder(id);
	}

	@Operation(summary = "API For Purchase Order Disapproval")
	@PutMapping(value = "/disapprovePurchaseOrder", consumes = "application/json")
	public ResponseEntity<String> disapprovePurchaseOrder(@RequestParam Integer id, @RequestParam String reason) {
		return purchaseService.disapprovePurchaseOrder(id, reason);
	}

	// ============================= Purchase Order Approval End==================

	// ============================= Purchase Bill Start==================

	@Operation(summary = "API Purchase Bill Fetch")
	@GetMapping(value = "/getPurchaseBill")
	public List<PurchaseBillHeader> getPurchaseBill() {
		return purchaseService.getPurchaseBill();
	}

	@Operation(summary = "API Details Purchase Bill Fetch")
	@GetMapping(value = "/getDetailsPurchaseBill")
	public List<PurchaseBillHeader> getDetailsPurchaseBill() {
		return purchaseService.getDetailsPurchaseBill();
	}

	@Operation(summary = "API Purchase Bill Fetch By Month")
	@GetMapping(value = "/getPurchaseBillByMonth")
	public List<PurchaseBillHeader> getPurchaseBillByMonth(@RequestParam String fromDate, @RequestParam String todate) {
		return purchaseService.getPurchaseBillByMonth(fromDate, todate);
	}

	@Operation(summary = "API Purchase Bill Data Fetch By Id")
	@GetMapping(value = "/getPurchaseBillByid")
	public PurchaseBillHeader getPurchaseBillByid(@RequestParam Integer id) {
		return purchaseService.getPurchaseBillByid(id);
	}

	@Operation(summary = "API Purchase Bill Data Fetch By Id For View")
	@GetMapping(value = "/getPurchaseBillByidForView")
	public PurchaseBillHeader getPurchaseBillByidForView(@RequestParam Integer id) {
		return purchaseService.getPurchaseBillByidForView(id);
	}

	@Operation(summary = "API Purchase Bill Data Save")
	@PostMapping(value = "/postPurchaseBill", consumes = "application/json")
	public ResponseEntity<String> postPurchaseBill(@Valid @RequestBody POHeader pOHeader) {
		return purchaseService.postPurchaseBill(pOHeader);
	}

	@Operation(summary = "API Purchase Bill Data Modify")
	@PutMapping(value = "/updatePurchaseBill", consumes = "application/json")
	public ResponseEntity<String> updatePurchaseBill(@Valid @RequestBody PurchaseBillHeader purchaseBillHeader) {
		return purchaseService.updatePurchaseBill(purchaseBillHeader);
	}

	@Operation(summary = "API Purchase Bill Data Deleted")
	@DeleteMapping(value = "/deletePurchaseBill")
	public ResponseEntity<String> deletePurchaseBill(@RequestParam Integer id) {
		return purchaseService.deletePurchaseBill(id);
	}

	// ============================= Purchase Bill End==================

	// ============================= Purchase Terms Start==================

	@Operation(summary = "API Purchase Terms Data Fetch")
	@GetMapping(value = "/getPurchaseTerms")
	public List<PurchaseTerms> getPurchaseTerms() {
		return purchaseService.getPurchaseTerms();
	}

	@Operation(summary = "API Purchase Terms Data Fetch By Id")
	@GetMapping(value = "/getPurchaseTermsByid")
	public PurchaseTerms getPurchaseTermsByid(@RequestParam Integer id) {
		return purchaseService.getPurchaseTermsByid(id);
	}

	@Operation(summary = "API Purchase Terms Data Save")
	@PostMapping(value = "/postPurchaseTerms", consumes = "application/json")
	public ResponseEntity<String> addPurchaseTerms(@Valid @RequestBody PurchaseTerms purchaseTerms) {
		return purchaseService.addPurchaseTerms(purchaseTerms);
	}

	@Operation(summary = "API Purchase Terms Data Modify")
	@PutMapping(value = "/updatePurchaseTerms", consumes = "application/json")
	public ResponseEntity<String> updatePurchaseTerms(@Valid @RequestBody PurchaseTerms purchaseTerms) {
		return purchaseService.updatePurchaseTerms(purchaseTerms);
	}

	@Operation(summary = "API Purchase Terms Data Deleted")
	@DeleteMapping(value = "/deletePurchaseTerms")
	public ResponseEntity<String> deletePurchaseTerms(@RequestParam Integer id) {
		return purchaseService.deletePurchaseTerms(id);
	}

	// ============================= Purchase Terms End==================

}
