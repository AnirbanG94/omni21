package com.omnichannel.app.model.master;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="tot_hdr")
public class TotHeader {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    
	private Integer vendorId;
	

	private String validFrom;
	

	private String validTo;
	

	private String validFor;
	

	private Boolean status;
	
	@Transient
	private String particulars;
	
	@Transient
	private Double tot;

	@Transient
	private Double onInvoice;
	
	@Transient
	private Double offInvoice;
	
	@OneToOne(mappedBy = "totHeader",cascade = CascadeType.ALL)
    private TotDetail totDetail;
	
	@OneToMany(mappedBy="header",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<TotArticle> totarticles;

}
