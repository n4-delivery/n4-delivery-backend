package com.sparta.n4delivery.order.dto.response;

import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문 정보를 담는 응답 DTO 클래스
 *
 * @since 2024-11-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long orderId;
    private OrderState orderState;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderDetailResponseDto> orderDetails;


    /**
     * Order 엔티티 객체와 주문 상세 정보 목록을 기반으로 OrderResponseDto 객체 생성
     *
     * @param order        Order 엔티티 객체
     * @param orderDetails 주문 상세 정보 목록 (OrderDetail 엔티티 목록)
     * @return 생성된 응답 DTO 객체
     * @since 2024-11-05
     */
    public static OrderResponseDto createResponseDto(Order order, List<OrderDetail> orderDetails) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setOrderId(order.getId());
        responseDto.setOrderState(order.getState());
        responseDto.setTotalPrice(order.getTotalPrice());
        responseDto.setCreatedAt(order.getCreatedAt());
        responseDto.setUpdatedAt(order.getUpdatedAt());
        responseDto.setOrderDetails(orderDetails.stream().map(OrderDetailResponseDto::createResponseDto).toList());
        return responseDto;
    }
}
