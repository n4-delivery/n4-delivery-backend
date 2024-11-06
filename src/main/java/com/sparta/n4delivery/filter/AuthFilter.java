package com.sparta.n4delivery.filter;

import com.sparta.n4delivery.common.util.JwtUtil;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT 기반 인증 처리 필터
 *
 * @since 2024-10-18
 */
@Order(3)
@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 요청 URI에 따라 인증 처리 여부를 판단하고,
     * 토큰 검증 후 사용자 정보를 추출하여 다음 필터로 전달합니다.
     *
     * @param request     요청 객체
     * @param response    응답 객체
     * @param chain 필터 체인
     * @throws IOException jwt 관련 에러
     * @since 2024-10-18
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String url = servletRequest.getRequestURI();
        if (ignorePage(url)) {
            log.info("인증 처리를 하지 않는 URL : {}", url);
        } else {
            setAttribute(servletRequest);
        }
        chain.doFilter(request, response); // 다음 Filter 로 이동
    }

    /**
     * 주어진 URL이 인증을 필요로 하지 않는 URL인지 판단합니다.
     *
     * @param url 검사할 URL
     * @return 인증이 필요하지 않으면 true, 아니면 false
     * @since 2024-10-18
     */
    private boolean ignorePage(String url) {
        // 회원 가입, 로그인 관련 API 는 인증 필요 없이 요청 진행
        if (!StringUtils.hasText(url))
            return true;
        return url.contains("api/users/login") ||
                url.startsWith("/css") ||
                url.startsWith("/js");
    }

    /**
     * 인증된 사용자 정보를 요청 속성에 설정합니다.
     *
     * @param req HTTP 서블릿 요청 객체
     * @since 2024-10-31
     */
    private void setAttribute(HttpServletRequest req) {
        String token = getToken(req);
        Claims claims = jwtUtil.getClaims(token);
        String email = claims.getSubject();
        User user = userRepository.findByEmail(email).orElse(null);
        req.setAttribute("user", user);
    }

    /**
     * HTTP 요청에서 쿠키 값으로 JWT 토큰을 가져옵니다.
     *
     * @param req HTTP 요청 객체
     * @return JWT 토큰 값, 없으면 null
     * @since 2024-10-18
     */
    private String getToken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JwtUtil.AUTHORIZATION_HEADER)) {
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8); // Encode 되어 넘어간 Value 다시 Decode
                }
            }
        }
        return null;
    }
}