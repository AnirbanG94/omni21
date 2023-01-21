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

import com.omnichannel.app.model.stock.PickListHeader;
import com.omnichannel.app.model.stock.StockTransferHeader;
import com.omnichannel.app.service.StockService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class StockTransferController {

    @Autowired
    StockService Service;

    // ============================= Transfer Out==================

    @Operation(summary = "API TO FETCH STOCK TRANSFER OUT DATA")
    @GetMapping(value = "/getPendingStockTransfer")
    public List<StockTransferHeader> getPendingStockTransfer() {
        return Service.getPendingStockTransfer();
    }

    @Operation(summary = "API TO FETCH PICK LIST FOR STOCK TRANSFER OUT")
    @GetMapping(value = "/getPickListForStockTransfer")
    public List<PickListHeader> getPickListForStockTransfer(@RequestParam Integer outletId) {
        return Service.getPickListForStockTransfer(outletId);
    }

    @Operation(summary = "API TO SAVE STOCK TRANSFER OUT DATA")
    @PostMapping(value = "/postStockTransferOut", consumes = "application/json")
    public ResponseEntity<String> postStockTransferOut(@Valid @RequestBody StockTransferHeader stock) {
        return Service.postStockTransferOut(stock);
    }

    @Operation(summary = "API TO UPDATE STOCK TRANSFER OUT DATA")
    @PutMapping(value = "/updateStockTransferOut", consumes = "application/json")
    public ResponseEntity<String> updateStockTransferOut(@Valid @RequestBody StockTransferHeader stock) {
        return Service.updateStockTransferOut(stock);
    }
    // ============================= Transfer Out==================
    // ============================= Transfer In==================

    @Operation(summary = "API TO FETCH STOCK TRANSFER IN")
    @GetMapping(value = "/getStockTransferIn")
    public List<StockTransferHeader> getStockTransferIn(@RequestParam Integer outletId) {
        return Service.getStockTransferIn(outletId);
    }

    @Operation(summary = "API TO SAVE STOCK TRANSFER IN DATA")
    @PostMapping(value = "/postStockTransferIn", consumes = "application/json")
    public ResponseEntity<String> postStockTransferIn(@Valid @RequestBody StockTransferHeader stock) {
        return Service.postStockTransferIn(stock);
    }

    @Operation(summary = "API TO UPDATE STOCK TRANSFER OUT DATA")
    @PutMapping(value = "/updateStockTransferIn", consumes = "application/json")
    public ResponseEntity<String> updateStockTransferIn(@Valid @RequestBody StockTransferHeader stock) {
        return Service.updateStockTransferIn(stock);
    }

    // ============================= Transfer IN==================

}
