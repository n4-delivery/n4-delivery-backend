package com.sparta.n4delivery.order.dto.response;

import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상세 주문 정보를 담는 응답 DTO 클래스
 *
 * @since 2024-11-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDto {
    private Long id;
    private Long orderId;
    private Long menuId;
    private Integer count;
    private Integer price;

    public static OrderDetailResponseDto createResponseDto(OrderDetail orderDetail) {
        OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
        orderDetailResponseDto.setId(orderDetail.getId());
        orderDetailResponseDto.setOrderId(orderDetail.getOrder().getId());
        orderDetailResponseDto.setMenuId(orderDetail.getMenu().getId());
        orderDetailResponseDto.setCount(orderDetail.getCount());
        orderDetailResponseDto.setPrice(orderDetail.getPrice());
        return orderDetailResponseDto;
    }
}
