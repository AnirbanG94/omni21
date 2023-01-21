package com.omnichannel.app.model.vendor;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "ven_ship_info")
public class ShippingNotification extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ship_info_id")
	private Integer id;
	
	@NotNull
	@Column(name = "vendor_id")
	private Integer vendorId;
	
	@Column(name = "ship_info")
	private String shipInfo;
	
	@Column(name = "outlet_id")
	private Integer outletId;
	
	@Column(name = "tr_date")
	private LocalDate date;
	
	@Column(name = "tr_time")
	private LocalTime time;
	
	@Column(name = "po_no")
	private String poNo;
	
	@Column(name = "po_dt")
	private String poDate;
	
	private String docLink;
	
	private Boolean status;
	
	@Transient
	private String name;
	
	@Transient
	private String outletName;
}
