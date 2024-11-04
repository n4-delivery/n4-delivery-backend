package com.sparta.n4delivery.order.controller;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.order.dto.request.RequestCreateOrderDto;
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
@RequiredArgsConstructor // UserService 객체를 의존성 주입 방식으로 받아오는 코드 생략 가능
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/stores/{storeId}/orders")
    public ResponseEntity<OrderResponseDto> createOrder(
            HttpServletRequest req,
            @PathVariable Long storeId,
            @RequestBody RequestCreateOrderDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(req, storeId, requestDto));
    }

    @GetMapping("/orders")
    public ResponseEntity<PageResponseDto<List<OrderResponseDto>>> searchOrders(
            HttpServletRequest req,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.searchOrders(req, page - 1, size));
    }

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
