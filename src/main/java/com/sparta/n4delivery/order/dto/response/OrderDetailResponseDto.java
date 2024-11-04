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

    /**
     * 조회 결과를 기반으로 응답 DTO 생성
     *
     * @param orderDetail OrderDetail 엔티티 객체
     * @return 생성된 응답 DTO 객체
     * @since 2024-11-05
     */
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