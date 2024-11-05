package com.sparta.n4delivery.order.dto.request;

import com.sparta.n4delivery.enums.OrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상태 수정 요청 DTO 클래스입니다.
 *
 * @since 2024-11-05
 */
@Getter
@NoArgsConstructor
public class OrderUpdateRequestDto {
    private OrderState state;
}
