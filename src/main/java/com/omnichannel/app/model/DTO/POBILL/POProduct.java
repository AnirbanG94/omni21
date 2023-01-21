package com.omnichannel.app.model.DTO.POBILL;

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
public class POProduct {
	
	private Integer id;
	private Integer hdrId;
	private Integer dtlId;
	private Integer vendorId;
	private Integer articleId;
	private String articleName;
	private String articleShortName;
	private String eanCD;
	private String hsnCD;
	private Integer uomId;
	private Double mrp;
	private Double cost;
	private Double gst_tax;
	
	private Boolean checked_state;
	
	private String uomName;

}
