package com.sparta.n4delivery.menu.dto;

import com.sparta.n4delivery.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String state;

    public static MenuResponseDto from(Menu menu) {
        return new MenuResponseDto(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getState().name()
        );
    }
}
