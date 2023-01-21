package com.omnichannel.app.model.product;

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
@Table(name = "pro_article_stat_change")
@EntityListeners(AuditingEntityListener.class)
public class ArticleStatus extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Column(name = "article_id")
	private Integer articleid;
	
	@NotNull
	@Column(name = "product_id")
	private Integer productid;
	
	private Boolean status;
	
	private String sta_st_dt;
	
	private String sta_end_dt;
	
	private String sta_change_by;
	
	@Column(name = "block_procurement")
	private Boolean blockProcurement;
	
	private String block_st_dt;
	
	private String block_end_dt;
	
	private String block_change_by;
	
	private String narration;
	
	
	@Transient
	private String pro_division;
	
	@Transient
	private String pro_family;
	
	@Transient
	private String pro_class;
	
	@Transient
	private String pro_sub_class;
	
	@Transient
	private String articleName;
	

}
