package com.sparta.n4delivery.store.dto;

import lombok.Data;

@Data
public class StoreDto {
  private String name;
  private String openedAt;
  private String closedAt;
  private int minimumAmount;

  public StoreDto(String name, String openedAt, String closedAt, Integer minimumAmount) {
    this.name = name;
    this.openedAt = openedAt;
    this.closedAt = closedAt;
    this.minimumAmount = minimumAmount;
  }
}
