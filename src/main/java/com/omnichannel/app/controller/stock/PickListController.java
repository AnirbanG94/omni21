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

import com.omnichannel.app.model.stock.PickListHeader;
import com.omnichannel.app.service.StockService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class PickListController {

    @Autowired
    StockService Service;

    @Operation(summary = "API TO FETCH PICK LIST")
    @GetMapping(value = "/getPickList")
    public List<PickListHeader> getPickList() {
        return Service.getPickList();
    }

    @Operation(summary = "API TO FETCH ASSET NO AS PER ARTICLE")
    @GetMapping(value = "/getAssetNoByArticle")
    public List<String> getAssetNoByArticle(@RequestParam Integer articleId) {
        return Service.getAssetNoByArticle(articleId);
    }

    @Operation(summary = "API TO FETCH PICK LIST BY ID")
    @GetMapping(value = "/getPickListById")
    public PickListHeader getPickListById(@RequestParam Integer id) {
        return Service.getPickListById(id);
    }

    @Operation(summary = "API TO SAVE PICK LIST DATA")
    @PostMapping(value = "/postPickList", consumes = "application/json")
    public ResponseEntity<String> postPickList(@Valid @RequestBody PickListHeader list) {
        return Service.postPickList(list);
    }

    @Operation(summary = "API TO UPDATE PICK LIST DATA")
    @PutMapping(value = "/updatePickList", consumes = "application/json")
    public ResponseEntity<String> updatePickList(@Valid @RequestBody PickListHeader list) {
        return Service.updatePickList(list);
    }

    @Operation(summary = "API TO DELETE PICK LIST DATA")
    @DeleteMapping(value = "/deletePickList")
    public String deletePickList(@RequestParam Integer id) {
        return Service.deletePickList(id);
    }
    
}
