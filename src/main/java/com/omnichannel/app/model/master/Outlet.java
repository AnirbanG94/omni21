package com.omnichannel.app.model.master;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "mst_outlet")
@EntityListeners(AuditingEntityListener.class)
public class Outlet extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String type;

	@NotNull
	private String name;

	@NotNull
	private String add1;
	private String add2;
	private String add3;

	private Integer locationid;
	private Integer stateid;
	private Integer countrycode;

	@NotNull
	private Integer pin;

	private String phone;

	@NotNull
	private String mobile;

	@NotNull
	private String email1;

	@Column(columnDefinition = "integer default 0")
	private Integer warehouseid;

	private Boolean status;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "outlet")
	@JsonIgnoreProperties("outlet")
	private List<Cluster> cluster;
	
    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<OutletDocDetails> details;

	@Transient
	private String locationname;
	@Transient
	private String statename;
	@Transient
	private String countryname;

	@Transient
	private String warehousename;

}
