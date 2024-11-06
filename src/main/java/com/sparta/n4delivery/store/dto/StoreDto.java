package com.sparta.n4delivery.store.dto;

import com.sparta.n4delivery.menu.dto.MenuResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class StoreDto {
    private String name;
    private String openedAt;
    private String closedAt;
    private int minimumAmount;
    private List<MenuResponseDto> menus;

    public StoreDto(String name, String openedAt, String closedAt, Integer minimumAmount) {
        this.name = name;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minimumAmount = minimumAmount;
    }
}
