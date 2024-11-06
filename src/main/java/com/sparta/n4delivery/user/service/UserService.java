package com.sparta.n4delivery.user.service;

import com.sparta.n4delivery.common.util.JwtUtil;
import com.sparta.n4delivery.user.dto.ResponseUserDto;
import com.sparta.n4delivery.user.dto.UserDto;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseUserDto login(HttpServletResponse res, UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(userDto.getPassword())) { // 비밀번호 검증
                addJwtToCookie(user, res);
                return new ResponseUserDto(user.getId(), user.getNickname(), user.getEmail());
            }
        }
        throw new RuntimeException("Invalid email or password"); // 예외 처리
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
}
