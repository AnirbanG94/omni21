package com.omnichannel.app.model.DTO;

import java.util.List;

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
public class ClusterMapping {
	
	private Integer clusterId;
	private List<Integer> outletId;
	private List<Integer> articleId;
	

}
