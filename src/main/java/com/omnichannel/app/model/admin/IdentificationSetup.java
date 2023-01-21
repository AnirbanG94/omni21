package com.omnichannel.app.model.admin;

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
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "adm_identification")
public class IdentificationSetup extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	@NotNull
	@Column(name = "menu_id")
	private Integer menu;
	
	
    private Boolean tradingVendor;
	
    @NotNull
	@Column(name = "yr_cd")
	private  String year;
	
	@NotNull
	private  Boolean isYearUse;
	

	@Column(name = "prefix")
	private String prefix;
	

	@Column(name = "suffix")
	private String sufix;
	

	@Column(name = "sl_no")
	private String slno;
	
	private String separatedBy;
	

	@Column(name = "no_Of_length")
	private String noOflength;
	

	private Boolean status;
	
	@Transient
	private String manuname;

}
