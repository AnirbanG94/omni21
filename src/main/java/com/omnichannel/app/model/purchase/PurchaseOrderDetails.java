 package com.omnichannel.app.model.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omnichannel.app.model.product.Articles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "pur_po_dtl")
public class PurchaseOrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="po_dtl_id")
	private Integer id;
	
	@Column(name="article_id")
	private Integer articleId;
	
	@Column(name="hsn_cd")
	private String  hsnCd;
	
	@Column(name="ean_cd")
	private String  eanCd;
	
	@Column(name="uom_id")
	private Integer  uomId;
	
	private Double mrp;
	private Double  qty;
	private Double  cp;
	
	@Column(name="basic_amt")
	private Double  basicAmt;
	
	@Column(name="tax_per")
	private Double  taxPer;
	
	@Column(name="sgst_amt")
	private Double  sgstAmt;
	
	@Column(name="cgst_amt")
	private Double  cgstAmt;
	
	@Column(name="igst_amt")
	private Double  igstAmt;
	
	@Column(name="net_amt")
	private Double netAmt;
	
	@Column(name="recd_qty")
	private Double recdQty;
	
	private Boolean active;
	
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "po_hdr_id")
	private PurchaseOrderHeader header;
    
    @Transient
    private Articles articles;
    
    @Transient
    private String uomName;
    
    @Transient
    private String articleName;
	
}
