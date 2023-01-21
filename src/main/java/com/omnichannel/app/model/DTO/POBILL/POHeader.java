package com.omnichannel.app.model.DTO.POBILL;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class POHeader {
	
	private Integer poHeaderId;
	private String deliv_dt;
	private String billDate;
	private String billNo;
	private String exp_dt;
	private Integer billing_id;
	private Integer ship_id;
	private Integer vendorId;
	private Double totBasicAmt;
	private Double totCgstAmt;
	private Double totSgstAmt;
	private Double totIgstAmt;
	private Double totNetAmt;
	private String note;
	private String paymTerms;
	
	private List<PODetails> product_details;
	
	private String poNo;
	
	private String poDt;
	
	
}
