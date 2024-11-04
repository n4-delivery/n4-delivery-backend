package com.sparta.n4delivery.order.dto.request;

import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetail;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RequestCreateOrderDto {
    List<RequestCreateOrderDetailDto> requestOrderDetails;

    /**
     * DTO 객체를 엔티티 객체로 변환
     *
     * @param user         주문자
     * @param store        주문 가게
     * @param orderDetails 주문한 메뉴들
     * @return 생성된 주문 엔티티 객체
     * @since 2024-11-04
     */
    public Order convertDtoToEntity(User user, Store store, List<OrderDetail> orderDetails) {
        int totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getPrice();
        }

        return Order.builder()
                .user(user)
                .store(store)
                .totalPrice(totalPrice)
                .build();
    }

    public List<OrderDetail> convertEntityToDto(List<Menu> menus) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (RequestCreateOrderDetailDto requestOrderDetail : requestOrderDetails) {
            for (Menu menu : menus) {
                if (menu.getId().equals(requestOrderDetail.getMenuId())) {
                    orderDetails.add(requestOrderDetail.convertDtoToEntity(menu));
                    break;
                }
            }
        }
        return orderDetails;
    }
}
