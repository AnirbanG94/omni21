package com.omnichannel.app.model.admin;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
@Table(name = "adm_tran_log")
public class Transactionlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tranlog_id")
	private Integer id;

	@NotNull
	@Column(name = "username")
	private String username;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	@NotNull
	private String activity;
	
	@Transient
	private String name;

}
