package com.sparta.n4delivery.user.controller;

import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDto> login(@RequestBody UserDto userDto) {
        ResponseUserDto responseUserDto = userService.login(userDto);
        return ResponseEntity.ok(responseUserDto);
    }
}
