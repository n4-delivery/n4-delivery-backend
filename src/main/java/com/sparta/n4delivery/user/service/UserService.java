package com.sparta.n4delivery.user.service;

import com.sparta.n4delivery.jwt.JwtUtil;
import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseUserDto login(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(userDto.getPassword())) { // 비밀번호 검증
                String token = jwtUtil.generateToken(user.getNickname()); // 변경: username 대신 nickname 사용
                return new ResponseUserDto(token, user.getId(), user.getNickname(), user.getEmail());
            }
        }
        throw new RuntimeException("Invalid email or password"); // 예외 처리
    }
}
