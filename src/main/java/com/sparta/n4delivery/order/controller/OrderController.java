package com.sparta.n4delivery.order.controller;

import com.sparta.n4delivery.order.dto.request.RequestCreateOrderDetailDto;
import com.sparta.n4delivery.order.dto.request.RequestCreateOrderDto;
import com.sparta.n4delivery.order.dto.response.ResponseCreateOrderDto;
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

    @PostMapping("/api/stores/{storeId}/orders")
    public ResponseEntity<ResponseCreateOrderDto> createOrder(
            HttpServletRequest req,
            @PathVariable Long storeId,
            @RequestBody RequestCreateOrderDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(req, storeId, requestDto));
    }
}
