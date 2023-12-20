package com.abhishek.blogapp.services.impl;

import com.abhishek.blogapp.services.FileService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;



@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // fetch file name
        String name = file.getOriginalFilename();
//        System.out.println("name : "+name);


        // get random name
        String randomID = UUID.randomUUID().toString();
        String randomImgName = randomID.concat(name.substring(name.lastIndexOf(".")));
//        System.out.println("imgExtension : "+imgExtension);

        // full path
        String filePath = path+ randomImgName;
        System.out.println("filePath :"+filePath);


        //create folder if not created
        File f = new File(path);

        if(!f.exists()){
            f.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return randomImgName;
    }

    @Override
    public InputStream getResources(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;

        InputStream is = new FileInputStream(fullPath);
        // db logic to return input stream in case if resource is saved in database.

        return is;
    }
}

