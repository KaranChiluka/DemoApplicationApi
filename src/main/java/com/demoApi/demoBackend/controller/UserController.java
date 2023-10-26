package com.demoApi.demoBackend.controller;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/currentUser")
    public UserDetailsBO getCurrentUser(@RequestParam String token){
        return userService.getCurrentUser(token);
    }
}
