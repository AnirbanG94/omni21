package com.omnichannel.app.model.purchase;

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
import com.omnichannel.app.model.admin.Company;
import com.omnichannel.app.model.master.Outlet;
import com.omnichannel.app.model.vendor.VendorRegistreation;

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
@Table(name = "pur_po_hdr")
public class PurchaseOrderHeader extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "po_hdr_id")
	private Integer id;

	@Column(name = "po_no")
	private String poNo;

	@Column(name = "po_dt")
	private String poDt;

	@Column(name = "vendor_id")
	private Integer vendorId;

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "outlet_id")
	private Integer outletId;

	@Column(name = "delv_dt")
	private String delvDt;

	@Column(name = "expiry_dt")
	private String expiryDt;

	@Column(name = "paym_terms")
	private String paymTerms;

	@Column(name = "note")
	private String note;

	@Column(name = "tot_basic_amt")
	private Double totBasicAmt;

	@Column(name = "tot_sgst_amt")
	private Double totSgstAmt;

	@Column(name = "tot_cgst_amt")
	private Double totCgstAmt;

	@Column(name = "tot_igst_amt")
	private Double totIgstAmt;

	@Column(name = "tot_net_amt")
	private Double totNetAmt;

	private String DisapproveReason;

	@Column(name = "status")
	private String status;

	@Column(name = "active")
	private Boolean active;

	private Boolean grnFlag;

	private Boolean gdnFlag;

	@OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
	private List<PurchaseOrderDetails> details;

	@Transient
	private List<PurchaseOrderDetails> orderDetails;

	@Transient
	private Company company;
	@Transient
	private Outlet outlet;
	@Transient
	private VendorRegistreation vendorRegistreation;

	@Transient
	private String outletName;

	@Transient
	private String vendorName;
}
