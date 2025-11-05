package org.example.stamppaw_backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.stamppaw_backend.user.entity.User;
import org.example.stamppaw_backend.user.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public String getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return "현재 로그인된 사용자: " + userDetails.getUsername();
    }
}
