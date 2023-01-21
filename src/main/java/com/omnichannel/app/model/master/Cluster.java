package com.omnichannel.app.model.master;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnichannel.app.audit.Auditable;
import com.omnichannel.app.model.product.Articles;

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
@Table(name = "mst_cluster")
@EntityListeners(AuditingEntityListener.class)
public class Cluster extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String des;

	private Boolean status;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "outlet_cluster_mapping", joinColumns = {
			@JoinColumn(name = "ClusterId") }, inverseJoinColumns = { @JoinColumn(name = "outletId") })
	@JsonIgnoreProperties("cluster")
	private List<Outlet> outlet;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "article_cluster_mapping", joinColumns = {
			@JoinColumn(name = "ClusterId") }, inverseJoinColumns = { @JoinColumn(name = "articleId") })
	@JsonIgnoreProperties("cluster")
	private List<Articles> article;
   
}
