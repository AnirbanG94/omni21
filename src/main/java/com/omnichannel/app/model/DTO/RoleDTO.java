package com.omnichannel.app.model.DTO;

import java.util.List;

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
public class RoleDTO {
	
	
	private Integer id;
	private String name;
	private String description;
	private String customPermissionsType;
	private List<Integer> module;
	private List<Integer> sub_module_id;
	private List<Integer> privilages; 
	

}
