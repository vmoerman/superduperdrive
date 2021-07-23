package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.lang.Integer.parseInt;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String addCredential(CredentialForm credentialForm, Model model, Authentication authentication) {

        User currentUser = userService.getUser(authentication.getName());
        Credential credential = new Credential();
        if (credentialForm.getCredentialid() != "") {
            credential.setCredentialid(parseInt(credentialForm.getCredentialid()));
        }
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());

        if (credential.getCredentialid() == 0) {
            credentialService.addCredential(credential, currentUser.getUserId());
            model.addAttribute("success", true);
            model.addAttribute("message", "Credential added!");
        } else {
            credentialService.editCredential(credential, currentUser.getUserId());
            model.addAttribute("success", true);
            model.addAttribute("message", "Credential edited!");
        }

        return "result";
    }

    @GetMapping("/deletecredential/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") int credentialid, Model model, Authentication authentication) {
        try {
            User currentUser = userService.getUser(authentication.getName());
            credentialService.deleteCredential(credentialid);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deleted succesfully!");
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "System error!" + e.getMessage());
            return "result";
        }

    }
}
