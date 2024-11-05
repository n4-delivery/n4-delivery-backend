package com.sparta.n4delivery.menu.dto;

import lombok.Data;

@Data
public class MenuResponseDto {
  private Long id;
  private String name;
  private Integer price;

  public MenuResponseDto(Long id, String name, Integer price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

}