package com.omnichannel.app.controller.report;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omnichannel.app.service.ReportService;

import net.sf.jasperreports.engine.JRException;

/**
 * @author rajsekhar & paramita
 *
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {
	
	@Autowired
	ReportService service;
	
    @GetMapping(value = "/poReport", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestParam Integer id) throws JRException, IOException {
        return service.exportPoReport(id);
    }
}
