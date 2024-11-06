package com.sparta.n4delivery.store.dto;

import com.sparta.n4delivery.menu.dto.MenuResponseDto;

import java.util.List;

import lombok.Data;


@Data
public class StoreDetailResponse {
    private Long id;
    private String name;
    private String openedAt;
    private String closedAt;
    private Integer minimumAmount;
    private List<MenuResponseDto> menus;

    public StoreDetailResponse(Long id, String name, String openedAt, String closedAt, Integer minimumAmount, List<MenuResponseDto> menus) {
        this.id = id;
        this.name = name;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minimumAmount = minimumAmount;
        this.menus = menus;
    }

}