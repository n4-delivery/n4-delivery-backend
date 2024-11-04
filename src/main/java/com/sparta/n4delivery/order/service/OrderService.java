package com.sparta.n4delivery.order.service;

import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.menu.repository.MenuRepository;
import com.sparta.n4delivery.order.dto.request.RequestCreateOrderDetailDto;
import com.sparta.n4delivery.order.dto.request.RequestCreateOrderDto;
import com.sparta.n4delivery.order.dto.response.OrderResponseDto;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetail;
import com.sparta.n4delivery.order.repository.OrderDetailsRepository;
import com.sparta.n4delivery.order.repository.OrderRepository;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * 주문 서비스 클래스
 *
 * @since 2024-11-04
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Transactional
    public OrderResponseDto createOrder(HttpServletRequest req, Long storeId, RequestCreateOrderDto requestDto) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).build();
        Store store = findStore(storeId);
        List<Menu> menus = searchOrderMenus(requestDto.getOrderDetails());

        Order order = requestDto.convertDtoToEntity(user, store);
        orderRepository.save(order);
        List<OrderDetail> orderDetails = requestDto.convertEntityToDto(order, menus);
        orderDetailsRepository.saveAll(orderDetails);
        return OrderResponseDto.createResponseDto(order, orderDetails);
    }

    public Store findStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_STORE)
        );

        if (store.getState() == StoreState.CLOSE) {
            throw new ResponseException(ResponseCode.CLOSED_STORE);
        }

        LocalTime now = LocalTime.now();
        if (!now.isAfter(store.getOpenedAt()) || !now.isBefore(store.getClosedAt())) {
            throw new ResponseException(ResponseCode.CLOSED_STORE);
        }

        return store;
    }

    public List<Menu> searchOrderMenus(List<RequestCreateOrderDetailDto> requestMenus) {
        return menuRepository.findAllByIdIn(
                requestMenus.stream()
                        .map(RequestCreateOrderDetailDto::getMenuId)
                        .toList());
    }
}
