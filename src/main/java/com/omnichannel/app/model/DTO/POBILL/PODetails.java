package com.omnichannel.app.model.DTO.POBILL;

import java.util.List;

import com.omnichannel.app.model.vendor.VendorProductDetails;

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
public class PODetails {
	
	private Integer poDetailId;
	private Integer articleId;
	private Integer uomId;
	private String hsnCD;
	private String eanCD;
	private Double mrp;
	private Double quantity;
	private Double cost;
	private Double basic_cost;
	private Double gst_tax;
	private Double sgst;
	private Double cgst;
	private Double igst;
	private Double net_amount;
	
	private String articleName;
	
	private List<VendorProductDetails> details;

}
