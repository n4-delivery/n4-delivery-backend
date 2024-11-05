package com.sparta.n4delivery.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto {
    private String token;  // JWT 토큰
    private String nickname; // 사용자 닉네임
}
