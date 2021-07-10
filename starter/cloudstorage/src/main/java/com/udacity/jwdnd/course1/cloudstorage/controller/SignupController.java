package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.forms.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignup(SignupForm signupForm, Model model)
    {
        // returns signup form page
        return "signup";
    }

    @PostMapping
    public String signup(SignupForm signupForm, Model model)
    {
        String signupError = null;


        if(!userService.isAvailable(signupForm.getUsername()))
        {
            signupError = "User already exists";
        }

        if(signupError == null)
        {
            int rowsAdded = userService.createUser(signupForm);
            if(rowsAdded < 0)
            {
                signupError = "Something went wrong creating user";
            }
        }

        if(signupError == null)
        {
            model.addAttribute("signupSuccess", true);
        }
        else
        {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
