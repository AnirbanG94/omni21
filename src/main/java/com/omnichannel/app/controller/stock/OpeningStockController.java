package com.omnichannel.app.controller.stock;

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

import com.omnichannel.app.model.stock.OpeningStock;
import com.omnichannel.app.service.StockService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class OpeningStockController {

    @Autowired
    StockService Service;

    @Operation(summary = "API TO FETCH OPENING STOCK")
    @GetMapping(value = "/getOpeningStock")
    public List<OpeningStock> getOpeningStock() {
        return Service.getOpeningStock();
    }

    @Operation(summary = "API TO FETCH OPENING STOCK BY ID")
    @GetMapping(value = "/getOpeningStockById")
    public OpeningStock getOpeningStockById(@RequestParam Integer id) {
        return Service.getOpeningStockById(id);
    }

    @Operation(summary = "API TO SAVE OPENING STOCK DATA")
    @PostMapping(value = "/postOpeningStock", consumes = "application/json")
    public ResponseEntity<String> postOpeningStock(@Valid @RequestBody OpeningStock stock) {
        return Service.postOpeningStock(stock);
    }

    @Operation(summary = "API TO UPDATE OPENING STOCK DATA")
    @PutMapping(value = "/updateOpeningStock", consumes = "application/json")
    public ResponseEntity<String> updateOpeningStock(@Valid @RequestBody OpeningStock stock) {
        return Service.updateOpeningStock(stock);
    }

    @Operation(summary = "API TO DELETE OPENING STOCK DATA")
    @DeleteMapping(value = "/deleteOpeningStock")
    public String deleteOpeningStock(@RequestParam Integer id) {
        return Service.deleteOpeningStock(id);
    }

}
