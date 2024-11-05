package com.sparta.n4delivery.reviwe.controller;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.reviwe.dto.request.ReviewCreateRequestDto;
import com.sparta.n4delivery.reviwe.dto.response.ReviewResponseDto;
import com.sparta.n4delivery.reviwe.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 리뷰 목록 조회(내가 작성한) API
     *
     * @param req  HTTP 요청 객체
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/reviews")
    public ResponseEntity<PageResponseDto<List<ReviewResponseDto>>> searchReviews(
            HttpServletRequest req,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.searchReviews(req, page - 1, size));
    }

    /**
     * 리뷰 목록 조회(가게) API
     *
     * @param req     HTTP 요청 객체
     * @param storeId 가게 ID
     * @param page    페이지 번호
     * @param size    페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<PageResponseDto<List<ReviewResponseDto>>> searchReviews(
            HttpServletRequest req,
            @PathVariable Long storeId,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.searchReviews(req, storeId, page - 1, size));
    }

    /**
     * 리뷰 목록 조회(주문 건수에 대한) API
     *
     * @param req     HTTP 요청 객체
     * @param orderId 주문 ID
     * @return 생성된 주문 정보
     * @since 2024-11-05
     */
    @GetMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> findReview(
            HttpServletRequest req,
            @PathVariable Long orderId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.findReview(req, orderId));
    }
}
