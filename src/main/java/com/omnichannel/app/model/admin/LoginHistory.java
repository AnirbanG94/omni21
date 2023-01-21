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
@Setter
@Getter
@ToString
@Entity
@Table(name = "adm_login_history")
public class LoginHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loghist_id")
	private Integer id;

	@NotNull
	@Column(name = "user_id")
	private String username;


	@Column(name = "login_dt_time")
	private LocalDateTime login_time;


	@Column(name = "logout_dt_time")
	private LocalDateTime logout_time;
	
	private String sessionid;
	
	@Transient
	private String name;
	
}
