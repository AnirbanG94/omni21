package com.omnichannel.app.controller.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.service.StockService;

@RestController
public class StockController {

    @Autowired
    StockService Service;

    
}
