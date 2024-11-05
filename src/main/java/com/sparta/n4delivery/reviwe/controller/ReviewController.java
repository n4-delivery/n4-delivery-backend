package com.sparta.n4delivery.reviwe.controller;

import com.sparta.n4delivery.reviwe.dto.request.ReviewCreateRequestDto;
import com.sparta.n4delivery.reviwe.dto.response.ReviewResponseDto;
import com.sparta.n4delivery.reviwe.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 리뷰 컨트롤러 클래스
 *
 * @since 2024-11-05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 주문 생성 API
     *
     * @param req        HTTP 요청 객체
     * @param storeId    가게 ID
     * @param orderId    주문 ID
     * @param requestDto 주문 생성 요청 DTO
     * @return 생성된 주문 정보
     * @since 2024-11-05
     */
    @PostMapping("/stores/{storeId}/orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(
            HttpServletRequest req,
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @RequestBody ReviewCreateRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(req, storeId, orderId, requestDto));
    }
}
