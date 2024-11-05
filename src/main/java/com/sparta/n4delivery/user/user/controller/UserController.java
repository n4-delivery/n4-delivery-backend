package com.sparta.n4delivery.user.controller;

import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.service.UserService;
import com.sparta.n4delivery.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDto> login(@RequestBody UserDto userDto) {
        // 로그인 처리 및 사용자 정보 반환
        ResponseUserDto responseUser = userService.login(userDto);

        // JWT 토큰 생성
        String token = jwtUtil.createToken(responseUser.getUsername());

        // 응답 헤더에 JWT 토큰 포함
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(responseUser);
    }
}
