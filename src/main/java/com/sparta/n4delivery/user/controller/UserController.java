package com.sparta.n4delivery.user.controller;

import com.sparta.n4delivery.user.dto.UserRequestDto;
import com.sparta.n4delivery.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRequestDto requestDto) {
        userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
