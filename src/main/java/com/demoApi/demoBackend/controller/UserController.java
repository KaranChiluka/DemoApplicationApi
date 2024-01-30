package com.demoApi.demoBackend.controller;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/currentUser")
    public UserDetailsBO getCurrentUser(@RequestParam String token){
        return userService.getCurrentUser(token);
    }

    @PostMapping("/signup")
    public UserDetailsBO CreateUser(@RequestBody SignupDto signupDto){
        return userService.createUser(signupDto);
    }

    @PatchMapping("/updateDetails")
    public SignupDto updateUserDetails(@RequestBody SignupDto signupDto){
        return userService.updateUser(signupDto);
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<String> uploadFiles(@RequestBody List<File> files){
        return ResponseEntity.ok("files uploaded");
    }
}
