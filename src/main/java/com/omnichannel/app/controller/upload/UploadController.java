package com.omnichannel.app.controller.upload;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.omnichannel.app.model.DTO.UploadDto.UpDownForm;
import com.omnichannel.app.service.UploadService;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    UploadService service;

    @PostMapping("/addFile")
    public ResponseEntity<?> uploadFile(@ModelAttribute UpDownForm form) throws Exception {

        System.out.println("Upload Module for:" + form.getModule());

        String result = null;
        try {
            result = service.uploadFile(form);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/uploadProfilePic")
    public ResponseEntity<?> uploadProfilePic(@ModelAttribute UpDownForm form) throws Exception {

        System.out.println("Upload Module for:" + form.getModule());

        String result = null;
        try {
            result = service.uploadProfilePic(form);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getFile")
    public ResponseEntity<Resource> getFile(@RequestParam String link) throws MalformedURLException {
        return service.getFile(link);
    }

    @PostMapping("/addListFiles")
    public ResponseEntity<?> addListFiles(@ModelAttribute UpDownForm form) throws Exception {

        System.out.println("Upload Module for:" + form.getModule());

        List<String> addListFiles = null;
        try {
            addListFiles = service.addListFiles(form);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(addListFiles, HttpStatus.OK);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<?> deleteFile(@RequestParam String link) throws Exception {

        return service.deleteFile(link);

    }
    
    @DeleteMapping("/deleteProfilePic")
    public ResponseEntity<?> deleteProfilePic(@RequestParam String name) throws Exception {

        return service.deleteProfilePic(name);

    }

}
