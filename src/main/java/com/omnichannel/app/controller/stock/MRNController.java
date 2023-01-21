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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.model.stock.MrnHeader;
import com.omnichannel.app.service.StockService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/mrn")
public class MRNController {

    @Autowired
    StockService Service;

    @Operation(summary = "API TO FETCH MRN")
    @GetMapping(value = "/getMRN")
    public List<MrnHeader> getMRN() {
        return Service.getMRN();
    }

    @Operation(summary = "API TO FETCH ARTICLE DETAILS")
    @GetMapping(value = "/getArticleDetails")
    public ResponseEntity<Object> getArticleDetails(@RequestParam Integer articleId) {
        return Service.getArticleDetails(articleId);
    }

    @Operation(summary = "API TO FETCH MRN BY ID")
    @GetMapping(value = "/getMRNById")
    public MrnHeader getMRNById(@RequestParam Integer id) {
        return Service.getMRNById(id);
    }

    @Operation(summary = "API TO SAVE MRN DATA")
    @PostMapping(value = "/postMRN", consumes = "application/json")
    public ResponseEntity<String> postMRN(@Valid @RequestBody MrnHeader mrn) {
        return Service.postMRN(mrn);
    }

    @Operation(summary = "API TO UPDATE MRN DATA")
    @PutMapping(value = "/updateMRN", consumes = "application/json")
    public ResponseEntity<String> updateMRN(@Valid @RequestBody MrnHeader mrn) {
        return Service.updateMRN(mrn);
    }

    @Operation(summary = "API TO DELETE MRN DATA")
    @DeleteMapping(value = "/deleteMRN")
    public String deleteMRN(@RequestParam Integer id) {
        return Service.deleteMRN(id);
    }

}
