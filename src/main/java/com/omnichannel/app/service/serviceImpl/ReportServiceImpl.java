package com.omnichannel.app.service.serviceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.omnichannel.app.config.NumberToWordExample;
import com.omnichannel.app.model.purchase.PurchaseOrderHeader;
import com.omnichannel.app.service.PurchaseService;
import com.omnichannel.app.service.ReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import pl.allegro.finance.tradukisto.MoneyConverters;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	PurchaseService serive;

	@Override
	public ResponseEntity<byte[]> exportPoReport(Integer id) throws JRException, IOException {
		PurchaseOrderHeader view = serive.getPurchaseOrderByidForView(id);

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(view.getDetails());

		Map<String, Object> parameters = new HashMap<>();

		parameters.put("pono", view.getPoNo());
		parameters.put("podt", view.getPoDt());
		parameters.put("payterms", view.getPaymTerms());

		parameters.put("totbasic", view.getTotBasicAmt());
		parameters.put("totcgst", view.getTotCgstAmt());
		parameters.put("totsgst", view.getTotSgstAmt());
		parameters.put("totigst", view.getTotIgstAmt());
		parameters.put("totnetamount", view.getTotNetAmt());

		parameters.put("vname", view.getVendorRegistreation().getName());
		parameters.put("vadress1", view.getVendorRegistreation().getAdd1());
		parameters.put("vadress2",
				view.getVendorRegistreation().getAdd2() + "," + view.getVendorRegistreation().getAdd3());
		parameters.put("vcity", view.getVendorRegistreation().getLocationname());
		parameters.put("vstate", view.getVendorRegistreation().getStatename());
		parameters.put("vpin", view.getVendorRegistreation().getPin().toString());
		parameters.put("vconper", view.getVendorRegistreation().getCont_pers());
		parameters.put("vconmobile", view.getVendorRegistreation().getCont_mobile());

		parameters.put("cname", view.getCompany().getName());
		parameters.put("cadress1", view.getCompany().getAddr1());
		parameters.put("cadress2", view.getCompany().getAddr2() + "," + view.getVendorRegistreation().getAdd3());
		parameters.put("ccity", view.getCompany().getLocationname());
		parameters.put("cstate", view.getCompany().getStatename());
		parameters.put("cpin", view.getCompany().getPincode().toString());
		parameters.put("cemail", view.getCompany().getEmail());
		parameters.put("cconmobile", view.getCompany().getMobile());
		parameters.put("comgst", view.getCompany().getGst());

		parameters.put("oname", view.getOutlet().getName());
		parameters.put("oadress1", view.getOutlet().getAdd1());
		parameters.put("oadress2", view.getOutlet().getAdd2() + "," + view.getVendorRegistreation().getAdd3());
		parameters.put("ocity", view.getOutlet().getLocationname());
		parameters.put("ostate", view.getOutlet().getStatename());
		parameters.put("opin", view.getOutlet().getPin().toString());
		parameters.put("oemail", view.getOutlet().getEmail1());
		parameters.put("oconmobile", view.getOutlet().getMobile());

		Long roundOff = (Long) Math.round(view.getTotNetAmt());
		String convertNumberToWord = NumberToWordExample.convertNumberToWord(roundOff);

		parameters.put("totinWord", convertNumberToWord);

		System.out.println(convertNumberToWord);
		
		InputStream employeeReportStream
		  = getClass().getResourceAsStream("/POReport.jrxml");
		JasperReport jasperReport
		  = JasperCompileManager.compileReport(employeeReportStream);
		
		//JRSaver.saveObject(jasperReport, "employeeReport.jasper");

		//JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("../src/main/resources/POReport.jrxml"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);

		// JRElementsVisitor.visitReport(compileReport, null)

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=" + view.getPoNo() + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

	}

}
