package com.sparta.n4delivery.reviwe.controller;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.login.LoginUser;
import com.sparta.n4delivery.reviwe.dto.request.ReviewRequestDto;
import com.sparta.n4delivery.reviwe.dto.response.ReviewResponseDto;
import com.sparta.n4delivery.reviwe.service.ReviewService;
import com.sparta.n4delivery.user.entity.User;
import jakarta.validation.Valid;
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
     * @param user       로그인한 유저
     * @param storeId    가게 ID
     * @param orderId    주문 ID
     * @param requestDto 주문 생성 요청 DTO
     * @return 생성된 주문 정보
     * @since 2024-11-05
     */
    @PostMapping("/stores/{storeId}/orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(
            @LoginUser User user,
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @RequestBody @Valid ReviewRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(user, storeId, orderId, requestDto));
    }

    /**
     * 리뷰 목록 조회(내가 작성한) API
     *
     * @param user 로그인한 유저
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/reviews")
    public ResponseEntity<PageResponseDto<List<ReviewResponseDto>>> searchReviews(
            @LoginUser User user,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.searchReviews(user, page - 1, size));
    }

    /**
     * 리뷰 목록 조회(가게) API
     *
     * @param user    로그인한 유저
     * @param storeId 가게 ID
     * @param page    페이지 번호
     * @param size    페이지 크기
     * @return 주문 목록과 페이지 정보
     * @since 2024-11-05
     */
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<PageResponseDto<List<ReviewResponseDto>>> searchReviews(
            @LoginUser User user,
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.searchReviews(user, storeId, page - 1, size));
    }

    /**
     * 리뷰 목록 조회(주문 건수에 대한) API
     *
     * @param user    로그인한 유저
     * @param orderId 주문 ID
     * @return 생성된 주문 정보
     * @since 2024-11-05
     */
    @GetMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> findReview(
            @LoginUser User user,
            @PathVariable Long orderId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.findReview(user, orderId));
    }

    /**
     * 리뷰 수정 API
     *
     * @param user       로그인한 유저
     * @param reviewId   업데이트할 리뷰의 ID
     * @param requestDto 수정할 정보
     * @return 업데이트된 리뷰 정보
     * @since 2024-11-05
     */
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @LoginUser User user,
            @PathVariable Long reviewId,
            @RequestBody @Valid ReviewRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.updateReview(user, reviewId, requestDto));
    }

    /**
     * 리뷰 삭제 API
     *
     * @param user     로그인한 유저
     * @param reviewId 삭제할 리뷰의 ID
     * @return 삭제된 리뷰 정보
     */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> deleteReview(
            @LoginUser User user,
            @PathVariable Long reviewId) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(reviewService.deleteReview(user, reviewId));
    }
}
