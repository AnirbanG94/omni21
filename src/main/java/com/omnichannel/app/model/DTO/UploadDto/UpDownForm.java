package com.omnichannel.app.model.DTO.UploadDto;


import org.springframework.web.multipart.MultipartFile;

import com.omnichannel.app.model.DTO.UserDto;

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
public class UpDownForm {
	
	private String module;

    private MultipartFile file;
    
    private MultipartFile[] files;
    
}
