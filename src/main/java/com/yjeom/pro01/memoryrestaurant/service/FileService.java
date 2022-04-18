package com.yjeom.pro01.memoryrestaurant.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {

    public String uploadFile(String uploadPath,String originalFileName, byte[] fileData)  throws Exception{
        UUID uuid=UUID.randomUUID();
        String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName=uuid.toString()+extension;
        String fileUploadFullUrl=uploadPath+"/"+savedFileName;
        FileOutputStream fos=new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile=new File(filePath);
        if(deleteFile.exists()){
            deleteFile.delete();
        }
    }
}
