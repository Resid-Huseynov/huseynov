package com.resid.huseynov.controller;

import com.resid.huseynov.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public User allAccess(@AuthenticationPrincipal User user) {

        return user;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User userAccess(@AuthenticationPrincipal User user) {

        return user;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User adminAcces(@AuthenticationPrincipal User user) {

        return user;
    }
}
