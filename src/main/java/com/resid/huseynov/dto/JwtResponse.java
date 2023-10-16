package com.resid.huseynov.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String jwt;

    private String username;

    private List<String> roles;
}
