package com.sparta.n4delivery.order.controller;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.login.LoginUser;
import com.sparta.n4delivery.order.dto.request.OrderCreateRequestDto;
import com.sparta.n4delivery.order.dto.request.OrderUpdateRequestDto;
import com.sparta.n4delivery.order.dto.response.OrderResponseDto;
import com.sparta.n4delivery.order.service.OrderService;
import com.sparta.n4delivery.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 주문 컨트롤러 클래스
 *
 * @since 2024-11-04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 생성 API
     *
     * @param user       로그인한 유저
     * @param storeId    가게 ID
     * @param requestDto 주문 생성 요청 DTO
     * @return 생성된 주문 정보\
     * @since 2024-11-05
     */
    @PostMapping("/stores/{storeId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
            @LoginUser User user,
            @PathVariable Long storeId,
            @RequestBody OrderCreateRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(user, storeId, requestDto));
    }

    /**
     * 주문 목록 조회(주문자) API
     *
     * @param user 로그인 유저
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/orders")
    public ResponseEntity<PageResponseDto<List<OrderResponseDto>>> searchOrders(
            @LoginUser User user,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.searchOrders(user, page - 1, size));
    }

    /**
     * 특정 가게의 주문 목록(가게) 조회 API
     *
     * @param storeId 가게 ID
     * @param page    페이지 번호
     * @param size    페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/stores/{storeId}/orders")
    public ResponseEntity<PageResponseDto<List<OrderResponseDto>>> searchOrders(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.searchOrders(storeId, page - 1, size));
    }

    /**
     * 주문 정보 업데이트 API
     * 주문 정보 상태를 업데이트합니다.
     *
     * @param orderId    업데이트할 주문의 ID
     * @param requestDto 업데이트 요청 정보
     * @return 업데이트된 주문 정보
     * @since 2024-11-05
     */
    @PutMapping("/order/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderUpdateRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.updateOrder(orderId, requestDto));
    }
}
