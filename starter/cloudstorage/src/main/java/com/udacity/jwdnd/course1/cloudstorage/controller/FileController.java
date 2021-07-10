package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService)
    {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/home")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, Model model) throws IOException {

        User currentUser = userService.getUser(authentication.getName());

        if(fileService.getFile(multipartFile.getOriginalFilename()) != null)
        {
            model.addAttribute("error", true);
            model.addAttribute("message", "Upload failed: File with this name already exists");
            return "result";
        }



        try
        {
            fileService.saveFile(multipartFile, currentUser.getUserId());
            model.addAttribute("success", true);
            model.addAttribute("message", "Uploaded succesfully!");
        }
        catch (Exception e)
        {
            model.addAttribute("error", true);
            model.addAttribute("message", "System error!" + e.getMessage());
            return "result";
        }
        model.addAttribute("files", fileService.getAllFiles(currentUser.getUserId()));
        return "result";
    }

    @GetMapping("/download/{fileid}")
    public ResponseEntity<Resource> download(@PathVariable("fileid") int fileid) {
        File file = fileService.getFileById(fileid);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFilename());
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);

    }
}
