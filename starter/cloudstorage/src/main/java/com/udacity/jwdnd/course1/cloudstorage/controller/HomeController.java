package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {

    private FileService fileService;
    private UserService userService;

    public HomeController(FileService fileService, UserService userService)
    {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome(Authentication authentication, Model model)
    {
        User currentUser = userService.getUser(authentication.getName());
        model.addAttribute("files", fileService.getAllFiles(currentUser.getUserId()));
        return "home";
    }


}
