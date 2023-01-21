package com.omnichannel.app.model.master;

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
@Table(name="tot_master")
@EntityListeners(AuditingEntityListener.class)
public class Tot {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name="particulars")
	private String particulars;
	

	@Column(name="gkm_all")
	private Boolean GkmAll;
	
	
	@Column(name="gkm_sp_item")
	private Boolean GkmSpItem;
	
	
	@Column(name="cost_all")
	private Boolean CostAll;
	
	
	
	@Column(name="cost_sp_item")
	private Boolean CostSpItem;
	
	
	private Boolean status;
	
    
	
	@Transient
	private Double tot;
	
	@Transient
	private Double onInvoice;
	
	@Transient
	private Double offInvoice;
	


}
