package com.sparta.n4delivery.order.service;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.menu.repository.MenuRepository;
import com.sparta.n4delivery.order.dto.request.OrderCreateRequestDto;
import com.sparta.n4delivery.order.dto.request.OrderDetailCreateRequestDto;
import com.sparta.n4delivery.order.dto.request.OrderUpdateRequestDto;
import com.sparta.n4delivery.order.dto.response.OrderResponseDto;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.entity.OrderDetail;
import com.sparta.n4delivery.order.repository.OrderDetailsRepository;
import com.sparta.n4delivery.order.repository.OrderRepository;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
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

    /**
     * 주문 생성
     *
     * @param user       현재 로그인한 유저
     * @param storeId    주문할 가게 ID
     * @param requestDto 주문 생성 요청 DTO
     * @return 생성된 주문 정보 (응답 DTO)
     * @since 2024-11-05
     */
    @Transactional
    public OrderResponseDto createOrder(User user, Long storeId, OrderCreateRequestDto requestDto) {
        Store store = findStore(storeId);
        validateOrderForCreate(store);
        List<Menu> menus = searchOrderMenus(requestDto.getOrderDetails());
        Order order = requestDto.convertDtoToEntity(user, store);
        orderRepository.save(order);
        List<OrderDetail> orderDetails = requestDto.convertEntityToDto(order, menus);
        orderDetailsRepository.saveAll(orderDetails);
        return OrderResponseDto.createOrderResponseDto(store, menus, order, orderDetails);
    }

    /**
     * 사용자 주문 목록 조회 메서드 (페이징 처리)
     *
     * @param user 현재 로그인한 유저
     * @param page 현재 페이지 번호
     * @param size 한 페이지당 조회할 주문 건수
     * @return 주문 목록 응답 DTO (페이징 정보 포함)
     * @since 2024-11-05
     */
    public PageResponseDto<List<OrderResponseDto>> searchOrders(User user, int page, int size) {
        Page<Order> orders = orderRepository.findAllByUserIdOrderByUpdatedAtDesc(user.getId(), PageRequest.of(page, size));
        return createPageResponseDto(orders);
    }

    /**
     * 가게 주문 목록 조회 메서드 (페이징 처리)
     *
     * @param storeId 조회할 가게 ID
     * @param page    현재 페이지 번호
     * @param size    한 페이지당 조회할 주문 건수
     * @return 주문 목록 응답 DTO (페이징 정보 포함)
     * @since 2024-11-05
     */
    public PageResponseDto<List<OrderResponseDto>> searchOrders(Long storeId, int page, int size) {
        Store store = findStore(storeId);
        Page<Order> orders = orderRepository.findAllByStoreIdOrderByUpdatedAtDesc(store.getId(), PageRequest.of(page, size));
        return createPageResponseDto(orders);
    }

    /**
     * 주문 상태 업데이트 메서드
     *
     * @param orderId    주문 ID
     * @param requestDto 주문 상태 업데이트 요청 DTO
     * @return 업데이트된 주문 정보
     * @throws ResponseException 주문이 존재하지 않거나 이미 처리된 경우 예외 발생
     * @since 2024-11-05
     */
    @Transactional
    public OrderResponseDto updateOrder(Long orderId, OrderUpdateRequestDto requestDto) {
        Order order = findOrder(orderId);
        order.updateState(requestDto.getState());
        return OrderResponseDto.createOrderResponseDto(order);
    }

    /**
     * 주문 정보 조회 메서드
     *
     * @param orderId 주문 ID
     * @return 조회된 주문 정보
     * @throws ResponseException 주문이 존재하지 않거나 이미 처리된 경우 예외 발생
     * @since 2024-11-05
     */
    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_ORDER)
        );
    }

    /**
     * 가게 정보 조회 메서드
     *
     * @param storeId 조회할 가게 ID
     * @return 조회된 가게 정보 (Store 엔티티)
     * @throws ResponseException 가게 존재하지 않거나 영업 시간 외일 경우 예외 발생
     * @since 2024-11-05
     */
    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_STORE)
        );
    }

    /**
     * 주문 상세 정보 (메뉴) 목록 조회 메서드
     *
     * @param requestMenus 주문 생성 요청 DTO 목록
     * @return 조회된 메뉴 정보 목록
     * @since 2024-11-05
     */
    public List<Menu> searchOrderMenus(List<OrderDetailCreateRequestDto> requestMenus) {
        return menuRepository.findAllByIdIn(
                requestMenus.stream()
                        .map(OrderDetailCreateRequestDto::getMenuId)
                        .toList());
    }

    /**
     * 가게 생성 가능 여부 검증 메서드
     *
     * @param store 검증할 가게 정보
     * @throws ResponseException 주문 생성 불가능한 경우 예외를 던집니다.
     */
    private void validateOrderForCreate(Store store) {
        if (store.getState() == StoreState.CLOSE) {
            throw new ResponseException(ResponseCode.CLOSED_STORE);
        }

        LocalTime now = LocalTime.now();
        if ((!now.isAfter(store.getOpenedAt()) || !now.isBefore(store.getClosedAt()))) {
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("영업시간: ");
            errorMsg.append(store.getOpenedAt());
            errorMsg.append(" ~ ");
            errorMsg.append(store.getClosedAt());
            throw new ResponseException(ResponseCode.CLOSED_STORE, errorMsg.toString());
        }
    }

    /**
     * 주문 목록 응답 DTO 생성 메서드
     *
     * @param orders 주문 엔티티 페이지 정보
     * @return 주문 목록 응답 DTO (페이징 정보 포함)
     * @since 2024-11-05
     */
    private PageResponseDto<List<OrderResponseDto>> createPageResponseDto(Page<Order> orders) {
        List<OrderResponseDto> responseOrders = new ArrayList<>();
        for (Order order : orders)
            responseOrders.add(OrderResponseDto.createOrderResponseDto(order));
        return PageResponseDto.of(responseOrders, orders.getPageable(), orders.getTotalPages());
    }
}
