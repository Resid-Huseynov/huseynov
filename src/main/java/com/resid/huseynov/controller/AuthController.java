package com.resid.huseynov.controller;

import com.resid.huseynov.dto.AuthenticationDto;
import com.resid.huseynov.dto.JwtResponseDto;
import com.resid.huseynov.repository.RoleRepository;
import com.resid.huseynov.repository.UserRepository;
import com.resid.huseynov.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDto> getToken(@RequestBody AuthenticationDto registerDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDto.getUsername(), registerDto.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        JwtResponseDto jwtResponseDto = new JwtResponseDto(jwt);
        return ResponseEntity.ok(jwtResponseDto);
    }

}
