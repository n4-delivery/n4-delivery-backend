package com.sparta.n4delivery.menu.dto;

import com.sparta.n4delivery.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String state;
    private LocalDateTime deletedAt;

    public static MenuResponseDto from(Menu menu) {
        return new MenuResponseDto(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getState().name(),
                menu.getDeletedAt()
        );
    }
}
