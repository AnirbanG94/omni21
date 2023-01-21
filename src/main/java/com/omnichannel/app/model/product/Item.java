package com.omnichannel.app.model.product;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pro_item",uniqueConstraints = { @UniqueConstraint(name = "item", columnNames = { "pro_division", "pro_family","pro_class","pro_sub_class" }) })
public class Item extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(length = 100)
	private String pro_division;
	
	@NotNull
	@Column(length = 100)
	private String pro_family;
	
	@NotNull
	@Column(length = 100)
	private String pro_class;
	
	@NotNull
	@Column(length = 100)
	private String pro_sub_class;
	
	private Boolean status;
	
	
	
	

}
