package com.sparta.n4delivery.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStoreDto {
    private Long id;
    private String name;
    private String openedAt;
    private String closedAt;
    private Integer minimumAmount;

    public ResponseStoreDto(Long id, String name, String openedAt, String closedAt, Integer minimumAmount) {
        this.id = id;
        this.name = name;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minimumAmount = minimumAmount;
    }
}
