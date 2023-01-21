package com.omnichannel.app.model.DTO;

import java.util.List;

import javax.persistence.Column;
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
public class UserDto {
	
	private Integer id;
	
	@NotNull
	private String username;
	
	private String password;
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String mobile;
	
	private Integer hod_id;
	
	private String employeeId;
	
	private String profilePicLink;
	
	private Integer locationId;
	
	private boolean hodstatus;	
	
	private List<String> role_ids;
	private List<String> module_id;
	private List<String> sub_module_id;
	private List<String> hoc_privileges_id;
	
	
}
