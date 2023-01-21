package com.omnichannel.app.model.ItemDTO;

import java.util.ArrayList;

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
public class NewItem {
	
	public String pro_division;
    public String pro_family;
    public String pro_class;
    public ArrayList<ProSubClass> pro_sub_class; 

}
