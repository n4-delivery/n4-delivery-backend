package com.sparta.n4delivery.menu.dto;

import com.sparta.n4delivery.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MenuResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String state;
    private LocalDateTime deletedAt;

    public MenuResponseDto(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static MenuResponseDto from(Menu menu) {
        MenuResponseDto responseDto = new MenuResponseDto();
        responseDto.setId(menu.getId());
        responseDto.setName(menu.getName());
        responseDto.setPrice(menu.getPrice());
        responseDto.setState(menu.getState().name());
        responseDto.setDeletedAt(menu.getDeletedAt());
        return responseDto;
    }
}
