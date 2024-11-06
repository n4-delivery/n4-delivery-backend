package com.sparta.n4delivery.user.service;

import com.sparta.n4delivery.common.util.JwtUtil;
import com.sparta.n4delivery.common.util.PasswordEncoder;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.user.dto.UserRequestDto;
import com.sparta.n4delivery.user.dto.UserResponseDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto login(HttpServletResponse res, UserRequestDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isEmpty())
            throw new ResponseException(ResponseCode.NOT_FOUND_USER);

        User user = userOptional.get();
        if(user.getDeletedAt() != null)
            throw new ResponseException(ResponseCode.NOT_FOUND_USER);

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            throw new ResponseException(ResponseCode.NOT_MATCH_PASSWORD); // 예외 처리

        addJwtToCookie(user, res);
        return UserResponseDto.from(user);
    }

    public UserResponseDto registerUser(UserRequestDto requestDto, UserType userType) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new ResponseException(ResponseCode.DUPLICATED_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 생성 및 저장
        User user = requestDto.convertDtoToEntity(encodedPassword, userType);
        userRepository.save(user);
        return UserResponseDto.from(user);
    }

    /**
     * 사용자 정보를 토큰으로 만들어 쿠키에 저장합니다.
     *
     * @param user 사용자 정보 객체 (User)
     * @param res  HTTP 응답 객체 (HttpServletResponse)
     * @since 2024-10-18
     */
    public void addJwtToCookie(User user, HttpServletResponse res) {
        String token = jwtUtil.createToken(user.getEmail(), user.getType());
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }

    @Transactional
    public UserResponseDto deleteUser(User user, UserRequestDto requestDto) {
        User deleteUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_USER)
        );

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new ResponseException(ResponseCode.NOT_MATCH_PASSWORD);

        deleteUser.delete();
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto updateUser(User user, UserRequestDto requestDto) {
        // 1. 삭제할 유저가 존재하는지 검색한다.
        User updateUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_USER)
        );

        // 2. 새로운 비밀번호를 암호화 한다.
        String newPassword = passwordEncoder.encode(requestDto.getPassword());

        // 3. requestDto있는 정보로 업데이트 해준다.
        updateUser.update(requestDto, newPassword);
        return UserResponseDto.from(user);
    }
}
