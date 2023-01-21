package com.omnichannel.app.model.vendor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "ven_product_dtl")
public class VendorProductDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ven_prod_dtl_id")
	private Integer id;
	
	
	@Column(name = "mrp")
	private Double mrp;
	
	@Column(name = "final_cost")
	private Double finalCost;
	
	@Column(name = "gst_per")
	private Double gstPer;
	 
	@Column(name = "basicCost")
	private Double basicCost;
	
	@Column(name = "status")
	private Boolean status;
	
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ven_prod_id")
	private VendorProductHeader header;

}
