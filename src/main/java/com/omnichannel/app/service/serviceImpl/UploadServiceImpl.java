package com.omnichannel.app.service.serviceImpl;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.omnichannel.app.model.DTO.UploadDto.UpDownForm;
import com.omnichannel.app.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    @Override
    public String uploadFile(UpDownForm form) throws Exception {
        UUID uuid = UUID.randomUUID();

        // Make sure directory exists!
        File uploadDir = new File(UPLOAD_DIR + "/" + form.getModule());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        MultipartFile file = form.getFile();
        String random = uuid.toString();
        String name = file.getOriginalFilename();

        System.out.println(name);
        String[] part = name.split("\\.");
        String extension = part[part.length - 1];
        System.out.println(extension);
        String filename = random + "." + extension;

        System.out.println(filename);

        if (file.isEmpty()) {
            return "file not found";
        } else {

            String uploadFilePath = UPLOAD_DIR + "/" + form.getModule() + "/" + filename;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            return uploadFilePath;
        }
    }

    @Override
    public ResponseEntity<Resource> getFile(String link) throws MalformedURLException {
        File file = new File(link);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }
        else {
            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        }

    }

    @Override
    public List<String> addListFiles(UpDownForm form) throws Exception {
        // Make sure directory exists!
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        MultipartFile[] files = form.getFiles();
        List<String> links = new ArrayList<>();
        for (MultipartFile file : files) {

            if (!file.isEmpty()) {
                String uploadFilePath = UPLOAD_DIR + "/" + form.getModule() + "/" + file.getOriginalFilename();
                int i = 1;
                while (true) {
                    File f = new File(uploadFilePath);
                    if (f.exists()) {
                        uploadFilePath = uploadFilePath + "(" + i + ")";
                        System.out.println(uploadFilePath);
                        i++;
                    } else {
                        break;
                    }
                }

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadFilePath);
                Files.write(path, bytes);
                links.add(uploadFilePath);
            }

        }

        return links;
    }

    @Override
    public String uploadProfilePic(UpDownForm form) throws Exception {
        // Make sure directory exists!
        UUID uuid = UUID.randomUUID();

        File uploadDir = new File(uploadDirectory + "/" + form.getModule());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        MultipartFile file = form.getFile();
        String random = uuid.toString();
        String name = file.getOriginalFilename();

        System.out.println(name);
        String[] part = name.split("\\.");
        String extension = part[part.length - 1];
        System.out.println(extension);
        String filename = random + "." + extension;

        System.out.println(filename);

        if (file.isEmpty()) {
            return "file not found";
        } else {

            String uploadFilePath = uploadDirectory + "/" + form.getModule() + "/" + filename;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            return filename;
        }
    }

    @Override
    public ResponseEntity<?> deleteFile(String link) throws Exception {

        File file = new File(link);

        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                return new ResponseEntity<>("File Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("File Cant be Deleted", HttpStatus.BAD_REQUEST);
            }
        }

        else {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<?> deleteProfilePic(String name) throws Exception {
        String uploadFilePath = uploadDirectory + "/" + "profilepic" + "/" + name;

        File file = new File(uploadFilePath);

        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                return new ResponseEntity<>("File Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("File Cant be Deleted", HttpStatus.BAD_REQUEST);
            }
        }

        else {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
    }

}
