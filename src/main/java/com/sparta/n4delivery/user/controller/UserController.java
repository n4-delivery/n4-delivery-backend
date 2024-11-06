package com.sparta.n4delivery.user.controller;

import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.login.LoginUser;
import com.sparta.n4delivery.user.dto.UserRequestDto;
import com.sparta.n4delivery.user.dto.UserResponseDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> login(
            HttpServletResponse res,
            @RequestBody UserRequestDto userDto) {
        UserResponseDto response = userService.login(res, userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/owners/register")
    public ResponseEntity<UserResponseDto> registerOwner(@RequestBody @Valid UserRequestDto requestDto) {
        UserResponseDto response = userService.registerUser(requestDto, UserType.OWNER);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto requestDto) {
        UserResponseDto response = userService.registerUser(requestDto, UserType.USER);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/users")
    public ResponseEntity<UserResponseDto> deleteUser(
            @LoginUser User user,
            @RequestBody UserRequestDto requestDto) {
        UserResponseDto response = userService.deleteUser(user, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDto> updateUser(
            @LoginUser User user,
            @RequestBody UserRequestDto requestDto) {
        UserResponseDto response = userService.updateUser(user, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
