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

/**
 * 주문 생성 요청 DTO 클래스입니다.
 * 주문 상세 정보를 담고 있으며, 주문 생성 시 사용됩니다.
 *
 * @since 2024-11-05
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {
    List<OrderDetailCreateRequestDto> orderDetails;

    /**
     * 현재 DTO 객체를 Order 엔티티 객체로 변환합니다.
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

    /**
     * 현재 DTO 객체의 주문 상세 정보를 기반으로 OrderDetail 엔티티 객체 목록을 생성합니다.
     *
     * @param order 주문 정보
     * @param menus 메뉴 목록
     * @return 생성된 OrderDetail 엔티티 객체 목록
     * @since 2024-11-05
     */
    public List<OrderDetail> convertEntityToDto(Order order, List<Menu> menus) {
        int totalPrice = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailCreateRequestDto orderMenu : this.orderDetails) {
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
