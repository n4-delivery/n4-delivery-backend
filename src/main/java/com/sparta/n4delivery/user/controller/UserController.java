package com.sparta.n4delivery.user.controller;

import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDto> login(
            HttpServletResponse res,
            @RequestBody UserDto userDto) {
        ResponseUserDto response = userService.login(res, userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
