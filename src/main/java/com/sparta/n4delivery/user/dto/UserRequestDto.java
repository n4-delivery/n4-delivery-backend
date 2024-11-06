package com.sparta.n4delivery.user.dto;

import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    public User convertDtoToEntity(String password, UserType userType) {
        return User.builder()
                .userName(username)
                .password(password)
                .email(email)
                .type(userType)
                .build();
    }
}
