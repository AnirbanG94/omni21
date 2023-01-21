package com.omnichannel.app.model.stock;

import java.util.List;

import javax.persistence.CascadeType;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "grn_hdr")
public class GrnHeader extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer outletId;
	private Integer vendorId;
	private Integer poId;
	private String grnNum;
	private String grnDate;
	private String billNo;
	private String billDate;
	private String carrierName;
	private String shipmentNo;
	private String roadPermitNo;
	private String transDocNo;
	private String transDocDate;
	private String Note;

	private Double totBasicAmt;
	private Double totSgstAmt;
	private Double totCgstAmt;
	private Double totIgstAmt;
	private Double totNetAmt;

	private Boolean active;

	@OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
	private List<GrnDetails> details;

	@Transient
	private String outletName;

	@Transient
	private String vendorName;

	@Transient
	private String poNo;

	@Transient
	private String poDt;
}
