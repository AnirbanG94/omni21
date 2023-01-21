package com.omnichannel.app.model.vendor;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.omnichannel.app.audit.Auditable;
import com.omnichannel.app.model.product.Manufacturer;

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
@Table(name = "ven_reg")
@EntityListeners(AuditingEntityListener.class)
public class VendorRegistreation extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
	private Integer id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	@NotNull
	private String add1;
	private String add2;
	private String add3;
	
	@NotNull
	private Integer locationid;
	@NotNull
	private Integer stateid;
	@NotNull
	private Integer countrycode;
	
	@NotNull
	private Integer pin;
	
	private String phone;
	
	@NotNull
	private String mobile;
	
	@NotNull
	private String email1;
	private String email2;
	
	private String fax;
	
	@NotNull
	private String gst_no;
	
	private Integer credit_days;
	
	private Integer paym_mode_id;
	
	private String cont_pers;
	
	private String cont_mobile;
	
	private String order_sch;
	
	private String delv_sch;
	
	private Integer lead_time;
	
	private Double min_ord_qty;
	
	private Double min_ord_val;
	
	private String pan_no;
	
	private String fssai_no;
	private String fsa_no;
	private String tin_no;
	private String bank_ac_no;
	
	private String bank_nm;
	
	private String bank_branch;
	
	private String bank_ifsc;
	
	private Integer paym_terms;
	
	private String paym_on;
	
	private String vendor_cd;
	
	private String inv_ven_id;
	
	@Column(length = 1)
	private String status;
	
	private String EditReason;
	
	private String DisapproveReason;
	
	private Boolean trade;
	
	private Integer invitee;
	
    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<VendorDocDetails> details;
    
    @ManyToMany 
    @JoinTable( 
        name = "venManuf", 
        joinColumns = @JoinColumn(
          name = "venId", referencedColumnName = "vendor_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "manufId", referencedColumnName = "manufac_id")) 
    private Collection<Manufacturer> manufacturer;

	
	@Transient
	private String locationname;
	@Transient
	private String statename;
	@Transient
	private String countryname;
	@Transient
	private String paymModeName;

}
