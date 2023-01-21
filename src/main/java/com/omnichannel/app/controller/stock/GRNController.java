package com.omnichannel.app.controller.stock;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.model.stock.GrnHeader;
import com.omnichannel.app.service.StockService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class GRNController {

    @Autowired
    StockService Service;

    // ============================= GRN Start==================

    @Operation(summary = "API TO FETCH PO For GRN")
    @GetMapping(value = "/getPOArticleGRN")
    public List<PurchaseOrderHeader> getPOArticleGRN(@RequestParam Integer id) {
        return Service.getPOArticleGRN(id);
    }

    @Operation(summary = "API TO FETCH DETAIIL PO GRN")
    @GetMapping(value = "/getPODetailForGRN")
    public PurchaseOrderHeader getPODetailForGRN(@RequestParam Integer id) {
        return Service.getPODetailForGRN(id);
    }

    @Operation(summary = "API TO FETCH GRN")
    @GetMapping(value = "/getGRN")
    public List<GrnHeader> getGRN() {
        return Service.getGRN();
    }

    @Operation(summary = "API TO FETCH GRN BY ID")
    @GetMapping(value = "/getGRNById")
    public GrnHeader getGRNById(@RequestParam Integer id) {
        return Service.getGRNById(id);
    }

    @Operation(summary = "API TO SAVE GRN DATA")
    @PostMapping(value = "/postGRN", consumes = "application/json")
    public ResponseEntity<String> postGRN(@Valid @RequestBody GrnHeader grn) {
        return Service.postGRN(grn);
    }

    @Operation(summary = "API TO UPDATE GRN DATA")
    @PutMapping(value = "/updateGRN", consumes = "application/json")
    public ResponseEntity<String> updateGRN(@Valid @RequestBody GrnHeader grn) {
        return Service.updateGRN(grn);
    }

    // ============================= GRN End==================

}
