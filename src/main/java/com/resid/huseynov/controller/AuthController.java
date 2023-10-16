package com.resid.huseynov.controller;

import com.resid.huseynov.dto.LoginRequest;
import com.resid.huseynov.dto.JwtResponse;
import com.resid.huseynov.dto.MessageResponse;
import com.resid.huseynov.dto.SignupRequest;
import com.resid.huseynov.entity.User;
import com.resid.huseynov.entity.Role;
import com.resid.huseynov.repository.RoleRepository;
import com.resid.huseynov.repository.UserRepository;
import com.resid.huseynov.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.builder()
                .jwt(jwt)
                .username(userDetails.getUsername())
                .roles(roles)
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        userRepository.save(User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .isEnabled(true)
                .authorities(List.of(Objects.requireNonNull(roleRepository.findByAuthority("ROLE_USER").orElse(null))))
                .build());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
