package com.lagou.edu.controller;

import com.lagou.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/register/{email}/{password}/{code}")
    public Boolean register(@PathVariable("email") String email ,@PathVariable("password") String password , @PathVariable("code") String code) {
        if(userService.isRegistered(email)){
            return  true;
        }
        return userService.register(email,password,code);
    }

    @GetMapping("/isRegistered/{email}")
    public Boolean isRegistered(@PathVariable("email") String email) {
        return userService.isRegistered(email);
    }

    @GetMapping("/login/{email}/{password}")
    public String login(@PathVariable("email") String email ,@PathVariable("password") String password) {
        String token = email + ":" + password;
        return userService.getEmailByToken(DigestUtils.md5DigestAsHex(token.getBytes()));
    }

    @GetMapping("/info/{token}")
    public String info(@PathVariable("token") String token ) {
        return userService.getEmailByToken(token);
    }

    @GetMapping("/sendCodeToEmailAndSave/{email}/{code}")
    public Boolean sendCodeToEmailAndSave(@PathVariable("email") String email , @PathVariable("code") String code) {
        return userService.sendCodeToEmailAndSave(email,code);
    }
}
