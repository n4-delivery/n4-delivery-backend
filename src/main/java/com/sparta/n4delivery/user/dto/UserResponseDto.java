package com.sparta.n4delivery.user.dto;

import com.sparta.n4delivery.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String nickname; // 추가: 닉네임
    private String email;

    public static UserResponseDto from(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setUserId(user.getId());
        responseDto.setNickname(user.getUserName());
        responseDto.setEmail(user.getEmail());
        return responseDto;
    }
}
