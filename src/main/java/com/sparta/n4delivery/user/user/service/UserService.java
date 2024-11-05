package com.sparta.n4delivery.user.service;

import com.sparta.n4delivery.jwt.JwtUtil;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String authenticateUser(UserDto userDto) {
        // 사용자가 입력한 username과 password로 사용자 찾기
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // JWT 토큰 생성
        return jwtUtil.generateToken(user.getUsername());
    }
}
