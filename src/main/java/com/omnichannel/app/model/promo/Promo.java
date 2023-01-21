package com.omnichannel.app.model.promo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.omnichannel.app.audit.Auditable;
import com.omnichannel.app.model.vendor.VendorProductDetails;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "promo")
public class Promo extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "promo_id")
	private Integer id;

	private Integer clusterId;
	
	private Integer outletId;
	
	@Column(name = "article_id")
	private Integer articleId;
	
	
	@Column(name = "vendor_id")
	private Integer vendorId;
	
	@Column(name = "offer_des")
	private String offerDes;
	
	@Column(name = "ven_offer_per")
	private Integer venOfferPer;
	
	private Integer venOfferPrice;
	
	@Column(name = "self_offer_per")
	private Integer selfOfferPer;
	
	private Integer selfOfferPrice;

	@Column(name = "offer_st_dt")
	private String offerStDt;
	
	@Column(name = "offer_end_dt")
	private String offerEndDt;
	
	@Column(name = "self_approval")
	private String selfApproval;
	
	@Column(name = "ven_approval")
	private String venApproval;
	
	@Column(name = "ven_dis_reason")
	private String venDisReason;
	
	@Column(name = "self_dis_reason")
	private String selfDisReason;
	
	private Boolean status;
	
	@Transient
	private String venName;
	
	@Transient
	private String locationName;
	
	@Transient
	private String itemName;
	
	@Transient
	private String articalName;
	
	@Transient
	private List<VendorProductDetails> details;
	
}
