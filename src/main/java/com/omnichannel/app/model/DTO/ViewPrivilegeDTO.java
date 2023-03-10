package com.omnichannel.app.model.DTO;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ViewPrivilegeDTO {
	
	private Integer id;
	private String name;
	private List<MenuDTO> menu;

}
