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
@Table(name = "mst_bay")
@EntityListeners(AuditingEntityListener.class)
public class Bay extends Auditable<String>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bay_id")
    private Integer id;
	
	@NotNull
    @Column(name = "bay_no")
    private String bayNo;
	
	@NotNull
    @Column(name = "outlet_id")
    private Integer outleId;
	
	@NotNull
    @Column(name = "class")
    private String productClass;
	
	@NotNull
    @Column(name = "des")
    private String des;
	
	private Boolean status ;
	
	@Transient
	private String outletname;

}
