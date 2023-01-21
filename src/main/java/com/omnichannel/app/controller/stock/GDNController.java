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
import com.omnichannel.app.model.stock.GdnHeader;
import com.omnichannel.app.service.StockService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class GDNController {

    @Autowired
    StockService Service;

    // ============================= GRN Start==================

    @Operation(summary = "API TO FETCH PO For GDN")
    @GetMapping(value = "/getPOArticleGDN")
    public List<PurchaseOrderHeader> getPOArticleGDN(@RequestParam Integer id) {
        return Service.getPOArticleGDN(id);
    }

    @Operation(summary = "API TO FETCH DETAIIL PO GDN")
    @GetMapping(value = "/getPODetailForGDN")
    public PurchaseOrderHeader getPODetailForGDN(@RequestParam Integer id) {
        return Service.getPODetailForGDN(id);
    }

    @Operation(summary = "API TO FETCH GDN")
    @GetMapping(value = "/getGDN")
    public List<GdnHeader> getGDN() {
        return Service.getGDN();
    }

    @Operation(summary = "API TO FETCH GDN BY ID")
    @GetMapping(value = "/getGDNById")
    public GdnHeader getGDNById(@RequestParam Integer id) {
        return Service.getGDNById(id);
    }

    @Operation(summary = "API TO SAVE GDN DATA")
    @PostMapping(value = "/postGDN", consumes = "application/json")
    public ResponseEntity<String> postGDN(@Valid @RequestBody GdnHeader gdn) {
        return Service.postGDN(gdn);
    }

    @Operation(summary = "API TO UPDATE GDN DATA")
    @PutMapping(value = "/updateGDN", consumes = "application/json")
    public ResponseEntity<String> updateGDN(@Valid @RequestBody GdnHeader gdn) {
        return Service.updateGDN(gdn);
    }

    // ============================= GRN End==================

}
