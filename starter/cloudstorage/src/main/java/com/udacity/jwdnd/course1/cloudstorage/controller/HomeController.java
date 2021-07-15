package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(EncryptionService encryptionService, CredentialService credentialService, FileService fileService, UserService userService, NoteService noteService)
    {
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String getHome(CredentialForm credentialForm,Note note, Authentication authentication, Model model)
    {
        User currentUser = userService.getUser(authentication.getName());
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getAllFiles(currentUser.getUserId()));
        model.addAttribute("notes", noteService.getAllNotes(currentUser.getUserId()));
        model.addAttribute("credentials", credentialService.getCredentials(currentUser.getUserId()));
        return "home";
    }


}
