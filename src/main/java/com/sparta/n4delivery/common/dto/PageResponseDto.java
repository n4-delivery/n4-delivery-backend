package com.sparta.n4delivery.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {
    private T data;
    private int page;
    private int size;
    private int totalPage;

    public static <T> PageResponseDto<T> of(T data, Pageable pageable, int totalPage) {
        return new PageResponseDto<>(data, pageable.getPageNumber() + 1, pageable.getPageSize(), totalPage);
    }
}
