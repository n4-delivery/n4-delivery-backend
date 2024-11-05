package com.sparta.n4delivery.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String nickname; // 추가: 닉네임
}
