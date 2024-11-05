package com.sparta.n4delivery.order.dto.response;

import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetail;
import com.sparta.n4delivery.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Long storeId;
    private String storeName;
    private OrderState orderState;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderDetailResponseDto> orderDetails;

    /**
     * 주문 정보를 담은 응답 DTO 객체 생성
     *
     * @param order 주문 엔티티
     * @return 생성된 주문 응답 DTO 객체
     * @since 2024-11-05
     */
    public static OrderResponseDto createOrderResponseDto(Order order) {
        Store store = order.getStore();
        List<Menu> menus = order.getStore().getMenus();
        List<OrderDetail> orderDetails = order.getOrderDetails();
        return createOrderResponseDto(store, menus, order, orderDetails);
    }

    /**
     * 주문 정보를 담은 응답 DTO 객체 생성
     *
     * @param store        주문이 속한 가게 정보
     * @param menus        주문에 포함된 메뉴 목록
     * @param order        주문 정보
     * @param orderDetails 주문 상세 정보 목록
     * @return 생성된 주문 응답 DTO 객체
     * @since 2024-11-05
     */
    public static OrderResponseDto createOrderResponseDto(Store store, List<Menu> menus, Order order, List<OrderDetail> orderDetails) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setStoreId(store.getId());
        responseDto.setStoreName(store.getName());
        responseDto.setOrderState(order.getState());
        responseDto.setTotalPrice(order.getTotalPrice());
        responseDto.setCreatedAt(order.getCreatedAt());
        responseDto.setUpdatedAt(order.getUpdatedAt());
        responseDto.setOrderDetails(createOrderDetailResponseDtoList(menus, orderDetails));
        return responseDto;
    }

    /**
     * 주문 상세 정보를 담은 응답 DTO 목록 생성
     *
     * @param menus        주문에 포함된 메뉴 목록
     * @param orderDetails 주문 상세 정보 목록
     * @return 주문 상세 응답 DTO 목록
     * @since 2024-11-05
     */
    public static List<OrderDetailResponseDto> createOrderDetailResponseDtoList(List<Menu> menus, List<OrderDetail> orderDetails) {
        List<OrderDetailResponseDto> orderDetailResponseDtoList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            for (Menu menu : menus) {
                if (menu.getId().equals(orderDetail.getMenu().getId())) {
                    orderDetailResponseDtoList.add(OrderDetailResponseDto.createResponseDto(menu, orderDetail));
                }
            }
        }
        return orderDetailResponseDtoList;
    }
}
