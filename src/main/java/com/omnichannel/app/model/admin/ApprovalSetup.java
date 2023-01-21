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
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "adm_approval_setup")
public class ApprovalSetup extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approval_id")
	private Integer id;

	@NotNull
	@Column(name = "menu_id")
	private Integer manuid;

	@NotNull
	@Column(name = "user_id")
	private Integer userid;

	@NotNull
	@Column(name = "approv_ord")
	private Integer approveorder;


	private Boolean status;
	
	@Transient
	private String menuname;
	@Transient
	private String username;
	

}
