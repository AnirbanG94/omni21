package com.omnichannel.app.model.product;

import java.util.List;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnichannel.app.audit.Auditable;
import com.omnichannel.app.model.master.Cluster;

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
@Table(name = "pro_article")
@EntityListeners(AuditingEntityListener.class)
public class Articles extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id")
	private Integer id;

	@NotNull
	@Column(name = "article_name")
	private String name;

	@NotNull
	@Column(name = "article_sh_nm")
	private String shortName;

	@NotNull
	@Column(name = "product_id")
	private Integer productId;

	@NotNull
	@Column(name = "ean_cd")
	private String eanCode;

	private Boolean status;

	private Boolean blockProcurement;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "article")
	@JsonIgnoreProperties("article")
	private List<Cluster> cluster;

	@Transient
	private String pro_division;

	@Transient
	private String pro_family;

	@Transient
	private String pro_class;

	@Transient
	private String pro_sub_class;

}
