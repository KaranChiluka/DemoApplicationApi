package com.demoApi.demoBackend.dto;

import lombok.Data;

@Data
public class SignupDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
}
