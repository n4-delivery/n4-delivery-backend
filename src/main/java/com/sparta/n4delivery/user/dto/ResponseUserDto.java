package com.sparta.n4delivery.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto {
    private Long userId;
    private String nickname; // 추가: 닉네임
    private String email;

    public ResponseUserDto(Long userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }
}
