package com.sparta.n4delivery.store.dto;


import lombok.Data;

@Data
public class StoreSummaryResponse {
    private Long id;
    private String name;
    private String openedAt;
    private String closedAt;
    private Integer minimumAmount;
    private String state;

    public StoreSummaryResponse(Long id, String name, String openedAt, String closedAt, Integer minimumAmount, String state) {
        this.id = id;
        this.name = name;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minimumAmount = minimumAmount;
        this.state = state;
    }
}