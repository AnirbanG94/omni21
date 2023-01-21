package com.omnichannel.app.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@ToString
@Getter
@Setter
@Entity
@Table(name="pro_uom")
@EntityListeners(AuditingEntityListener.class)
public class UOM extends Auditable<String>{
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="uom_id")
	private int id;
	
	@NotNull
	@Column(name="uom_short_name")
	private String shortName;
	
	@NotNull
	@Column(name="uom_name")
	private String name;
	

	private Boolean status ;
}
