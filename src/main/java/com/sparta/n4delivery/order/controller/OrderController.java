package com.sparta.n4delivery.order.controller;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.order.dto.request.OrderCreateRequestDto;
import com.sparta.n4delivery.order.dto.response.OrderResponseDto;
import com.sparta.n4delivery.order.service.OrderService;
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
     * @param req        HTTP 요청 객체
     * @param storeId    가게 ID
     * @param requestDto 주문 생성 요청 DTO
     * @return 생성된 주문 정보\
     * @since 2024-11-05
     */
    @PostMapping("/stores/{storeId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
            HttpServletRequest req,
            @PathVariable Long storeId,
            @RequestBody OrderCreateRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(req, storeId, requestDto));
    }

    /**
     * 주문 목록 조회(주문자) API
     *
     * @param req  HTTP 요청 객체
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/orders")
    public ResponseEntity<PageResponseDto<List<OrderResponseDto>>> searchOrders(
            HttpServletRequest req,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.searchOrders(req, page - 1, size));
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
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.searchOrders(storeId, page - 1, size));
    }
}
