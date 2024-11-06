package com.sparta.n4delivery.menu.dto;

import com.sparta.n4delivery.enums.MenuState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class MenuRequestDto {
    @NotBlank(message = "메뉴 이름은 필수입니다.")
    private String name;

    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private Integer price;

    private MenuState state;
}
