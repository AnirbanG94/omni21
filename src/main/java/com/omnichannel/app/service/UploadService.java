package com.omnichannel.app.service;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.omnichannel.app.model.DTO.UploadDto.UpDownForm;

public interface UploadService {

	public String uploadFile(UpDownForm form)throws Exception;

	public ResponseEntity<Resource> getFile(String link)throws MalformedURLException;

	public List<String> addListFiles(UpDownForm form)throws Exception;

	public String uploadProfilePic(UpDownForm form)throws Exception;

    public ResponseEntity<?> deleteFile(String link)throws Exception;

    public ResponseEntity<?> deleteProfilePic(String name)throws Exception;

}
