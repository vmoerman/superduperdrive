package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile multipartFile, int userid) throws IOException {


        File file = new File();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setContenttype(multipartFile.getContentType());
        file.setFilesize(multipartFile.getSize());
        file.setUserid(userid);
        file.setFiledata(multipartFile.getBytes());


        int addedFile = fileMapper.insert(file);
        return addedFile;
    }

    public File getFile(String filename)
    {
        return fileMapper.getFile(filename);
    }

    public File getFileById(int fileId)
    {
        return fileMapper.getFileById(fileId);
    }

    public List<File> getAllFiles(int userId)
    {
        return fileMapper.getAllFiles(userId);
    }

}
