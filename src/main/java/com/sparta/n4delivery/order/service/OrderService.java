package com.sparta.n4delivery.order.service;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.menu.repository.MenuRepository;
import com.sparta.n4delivery.order.dto.request.OrderCreateRequestDto;
import com.sparta.n4delivery.order.dto.request.OrderDetailCreateRequestDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
     * @param req        사용자 요청 (현재는 미사용 - 추후 쿠키 등에서 사용자 정보 추출 예정)
     * @param storeId    주문할 가게 ID
     * @param requestDto 주문 생성 요청 DTO
     * @return 생성된 주문 정보 (응답 DTO)
     * @since 2024-11-05
     */
    @Transactional
    public OrderResponseDto createOrder(HttpServletRequest req, Long storeId, OrderCreateRequestDto requestDto) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).build();
        Store store = findStore(storeId, false);
        List<Menu> menus = searchOrderMenus(requestDto.getOrderDetails());

        Order order = requestDto.convertDtoToEntity(user, store);
        orderRepository.save(order);
        List<OrderDetail> orderDetails = requestDto.convertEntityToDto(order, menus);
        orderDetailsRepository.saveAll(orderDetails);
        return OrderResponseDto.createResponseDto(order, orderDetails);
    }

    /**
     * 사용자 주문 목록 조회 메서드 (페이징 처리)
     *
     * @param req  사용자 요청 (현재는 미사용 - 추후 쿠키 등에서 사용자 정보 추출 예정)
     * @param page 현재 페이지 번호
     * @param size 한 페이지당 조회할 주문 건수
     * @return 주문 목록 응답 DTO (페이징 정보 포함)
     * @since 2024-11-05
     */
    public PageResponseDto<List<OrderResponseDto>> searchOrders(HttpServletRequest req, int page, int size) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).build();
        Page<Order> orders = orderRepository.findAllByUserOrderByUpdatedAtDesc(user, PageRequest.of(page, size));
        return createResponseDto(orders);
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
        Store store = findStore(storeId, true);
        Page<Order> orders = orderRepository.findAllByStoreOrderByUpdatedAtDesc(store, PageRequest.of(page, size));
        return createResponseDto(orders);
    }

    /**
     * 가게 정보 조회 메서드
     *
     * @param storeId     조회할 가게 ID
     * @param ignoreClose 영업 상태 확인 여부 (true: 무시, false: 확인)
     * @return 조회된 가게 정보 (Store 엔티티)
     * @throws ResponseException 가게 존재하지 않거나 영업 시간 외일 경우 예외 발생
     * @since 2024-11-05
     */
    public Store findStore(Long storeId, boolean ignoreClose) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_STORE)
        );

        if (store.getState() == StoreState.CLOSE && !ignoreClose) {
            throw new ResponseException(ResponseCode.CLOSED_STORE);
        }

        LocalTime now = LocalTime.now();
        if ((!now.isAfter(store.getOpenedAt()) || !now.isBefore(store.getClosedAt())) && !ignoreClose) {
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append("영업시간: ");
            errorMsg.append(store.getOpenedAt());
            errorMsg.append(" ~ ");
            errorMsg.append(store.getClosedAt());
            throw new ResponseException(ResponseCode.CLOSED_STORE, errorMsg.toString());
        }

        return store;
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
     * 주문 목록 응답 DTO 생성 메서드
     *
     * @param orders 주문 엔티티 페이지 정보
     * @return 주문 목록 응답 DTO (페이징 정보 포함)
     * @since 2024-11-05
     */
    private PageResponseDto<List<OrderResponseDto>> createResponseDto(Page<Order> orders) {
        List<OrderResponseDto> responseOrders = new ArrayList<>();
        for (Order order : orders)
            responseOrders.add(OrderResponseDto.createResponseDto(order, order.getOrderDetails()));
        return PageResponseDto.of(responseOrders, orders.getPageable(), orders.getTotalPages());
    }
}
