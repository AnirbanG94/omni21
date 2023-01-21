package com.omnichannel.app.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import net.sf.jasperreports.engine.JRException;

public interface ReportService {

	public ResponseEntity<byte[]> exportPoReport(Integer id)throws JRException, IOException;

}
