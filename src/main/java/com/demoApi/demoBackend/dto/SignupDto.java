package com.demoApi.demoBackend.dto;

import lombok.Data;

@Data
public class SignupDto {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}
