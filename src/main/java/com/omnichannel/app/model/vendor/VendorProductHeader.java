package com.omnichannel.app.model.vendor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.omnichannel.app.audit.Auditable;

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
@Table(name = "ven_product")
@EntityListeners(AuditingEntityListener.class)
public class VendorProductHeader extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_product_id")
	private Integer id;
	
	@Column(name = "vendor_id")
	private Integer vendorId;
	
	@Column(name = "article_id")
	private Integer articleId;
	
	@Column(name = "article_name")
	private String articleName;
	
	@Column(name = "article_sh_nm")
	private String articleShortName;
	
	@Column(name = "item_id")
	private Integer itemId;
	
	@Column(name = "ean_cd")
	private String eanCD;
	
	@Column(name = "brand_id")
	private Integer brandId;
	
	@Column(name = "manufac_id")
	private Integer manufacId;
	
	@Column(name = "hsn_cd")
	private String hsnCD;
	
	@Column(name = "uom_id")
	private Integer uomId;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "weight")
	private String weight;
	
	@Column(name = "prod_type")
	private String productType;
	
	@Column(name = "conces_ind")
	private String concesInd;
	
	@Column(name = "dsp_ind")
	private String dspInd;
	
	@Column(name = "on_inv_margin")
	private Double onInvMargin;
	
	@Column(name = "off_inv_margin")
	private Double offInvMargin;
	
	@Column(name = "dim_depth")
	private String dimDepth;
	
	@Column(name = "dim_width")
	private String dimWidth;
	
	@Column(name = "dim_height")
	private String dimHeight;
	
	@Column(name = "net_content")
	private String netContent;
	
	@Column(name = "carton_units")
	private String cartonUnits;
	
	@Column(name = "inner_units")
	private String innerUnits;
	
	@Column(name = "distr_type")
	private String distrType;
	
	@Column(name = "outer_case")
	private String outerCase;
	
	@Column(name = "inner_case")
	private String innerCase;
	
	@Column(name = "order_day")
	private String orderDay;
	
	@Column(name = "delv_day")
	private String delvDay;
	
	@Column(name = "movement")
	private String movement;
	
	@Column(name = "s_order_sch")
	private Integer storeOrderSch;
	
	@Column(name = "s_deliv_sch")
	private Integer storeDelivSch;
	
	@Column(name = "wh_order_sch")
	private Integer warehouseOrderSch;
	
	@Column(name = "wh_deliv_sch")
	private Integer warehouseDelivSch;
	
	@Column(name = "mbq")
	private Double mbq;
	
	@Column(name = "buying_pattern")
	private String buyingPattern;
	
	@Column(name = "price_based")
	private String priceBased;
	
	@Column(name = "return_type")
	private String returnType;
	
	@Column(name = "old_ean_cd")
	private String oldEanCd;
	
	private String reson;
	
	@Column(name = "status")
	private String status;
	
	private String productPic;
	
	@OneToMany(mappedBy="header",cascade = CascadeType.ALL)
	private List<VendorProductDetails> details;
	
	@Transient
	private String vendorName;
	
	@Transient
	private String pro_division;
	
	@Transient
	private String pro_family;
	
	@Transient
	private String pro_class;
	
	@Transient
	private String pro_sub_class;
	
	
	@Transient
	private String brandName;
	
	@Transient
	private String manufName;
	
	@Transient
	private String uomName;
	
	@Transient
	private VendorProductDetails tempDetails;

	

}
