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
