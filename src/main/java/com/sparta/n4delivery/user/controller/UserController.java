package com.sparta.n4delivery.user.controller;

import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDto> login(@RequestBody UserDto userDto) {
        ResponseUserDto response = userService.login(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
