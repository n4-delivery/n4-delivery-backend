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
    List<RequestCreateOrderDetailDto> orderMenus;

    /**
     * DTO 객체를 엔티티 객체로 변환
     *
     * @param user  주문자
     * @param store 주문 가게
     * @return 생성된 주문 엔티티 객체
     * @since 2024-11-04
     */
    public Order convertDtoToEntity(User user, Store store) {
        return Order.builder()
                .user(user)
                .store(store)
                .build();
    }

    public List<OrderDetail> convertEntityToDto(Order order, List<Menu> menus) {
        int totalPrice = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (RequestCreateOrderDetailDto orderMenu : orderMenus) {
            for (Menu menu : menus) {
                if (menu.getId().equals(orderMenu.getMenuId())) {
                    totalPrice += menu.getPrice() * orderMenu.getCount();
                    orderDetails.add(orderMenu.convertDtoToEntity(order, menu));
                    break;
                }
            }
        }

        order.setTotalPrice(totalPrice);
        return orderDetails;
    }
}
