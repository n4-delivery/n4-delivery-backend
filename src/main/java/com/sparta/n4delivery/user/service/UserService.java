package com.sparta.n4delivery.user.service;

import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.user.dto.UserRequestDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserRequestDto requestDto) {
        // 유저 이름 중복 확인
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new ResponseException("Username is already taken.");
        }

        // 이메일 중복 확인
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new ResponseException("Email is already registered.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 생성 및 저장
        User user = new User(requestDto.getUsername(), encodedPassword, requestDto.getEmail());
        userRepository.save(user);
    }
}