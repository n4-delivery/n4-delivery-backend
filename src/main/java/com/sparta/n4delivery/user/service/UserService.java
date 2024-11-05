package com.sparta.n4delivery.user.service;

import com.sparta.n4delivery.jwt.JwtUtil;
import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository; // UserRepository import
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // UserRepository 주입
    private final JwtUtil jwtUtil; // JwtUtil 주입
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더

    public ResponseUserDto login(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());
                ResponseUserDto responseUserDto = new ResponseUserDto();
                responseUserDto.setToken(token);
                responseUserDto.setNickname(user.getNickname());
                return responseUserDto;
            }
        }
        throw new RuntimeException("Invalid email or password");
    }
}
