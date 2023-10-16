package com.resid.huseynov.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {
    private String jwt;

    private String username;

    private List<String> roles;
}
