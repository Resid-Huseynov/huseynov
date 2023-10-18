package com.resid.huseynov.controller;

import com.resid.huseynov.dto.JwtResponse;
import com.resid.huseynov.dto.LoginRequest;
import com.resid.huseynov.dto.MessageResponse;
import com.resid.huseynov.dto.SignupRequest;
import com.resid.huseynov.entity.Role;
import com.resid.huseynov.entity.User;
import com.resid.huseynov.repository.RoleRepository;
import com.resid.huseynov.repository.UserRepository;
import com.resid.huseynov.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@CrossOrigin(origins = {"http://localhost:5173", "http://10.14.33.192:5173", "http://localhost:8081"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

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

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Error: Bu ad artıq mövcuddur!"));
        }

        userRepository.save(User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .isEnabled(true)
                .authorities(List.of(Objects.requireNonNull(roleRepository.findByAuthority("ROLE_EDITOR").orElse(null))))
                .build());

        return ResponseEntity.ok(new MessageResponse("İstifadəçi uğurla qeydiyyatdan keçmişdir!"));
    }

}
