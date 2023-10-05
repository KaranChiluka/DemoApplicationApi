package com.demoApi.demoBackend.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String token;
    private String username;
}
