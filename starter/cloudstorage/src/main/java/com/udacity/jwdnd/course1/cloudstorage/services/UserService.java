package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.forms.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    private HashService hashService;
    private UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }


    public int createUser(SignupForm signupForm) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(signupForm.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, signupForm.getUsername(), encodedSalt, hashedPassword, signupForm.getFirstName(), signupForm.getLastName()));
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public boolean isAvailable(String username) {
        return userMapper.getUser(username) == null;
    }
}
