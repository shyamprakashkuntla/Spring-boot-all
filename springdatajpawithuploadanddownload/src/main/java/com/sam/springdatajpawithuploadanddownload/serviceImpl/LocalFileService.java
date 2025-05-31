package com.sam.springdatajpawithuploadanddownload.serviceImpl;


import com.sam.springdatajpawithuploadanddownload.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class LocalFileService implements FileService {


    @Value("${local.file.path}")
    private String localFilePath;


    @Override
    public String uploadFile(MultipartFile multipartFile) {
        File localServerDir = new File(localFilePath);
        if (!localServerDir.exists()) {
            localServerDir.mkdirs();
        }

        File file = new File(localFilePath, multipartFile.getOriginalFilename());

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    @Override
    public InputStream downloadFile(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }

    @Override
    public void deleteFile(String certFilePath) {
        new File(certFilePath).delete();
    }
}
