package com.sparta.n4delivery.reviwe.service;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.repository.OrderRepository;
import com.sparta.n4delivery.reviwe.dto.request.ReviewRequestDto;
import com.sparta.n4delivery.reviwe.dto.response.ReviewResponseDto;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.reviwe.repository.ReviewRepository;
import com.sparta.n4delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 리뷰 서비스
 *
 * @since 2024-11-05
 */
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 생성 메서드
     *
     * @param user       로그인한 유저
     * @param storeId    리뷰를 남길 가게 식별자
     * @param orderId    리뷰를 남길 주문 식별자
     * @param requestDto 리뷰 생성 요청 DTO 객체
     * @return 생성된 리뷰 정보를 담은 응답 DTO 객체
     * @since 2024-11-05
     */
    public ReviewResponseDto createReview(User user, Long storeId, Long orderId, ReviewRequestDto requestDto) {
        Order order = findOrder(orderId);
        validateReviewForCreate(order.getId());
        Review review = requestDto.convertDtoToEntity(user, order, storeId);
        reviewRepository.save(review);
        return ReviewResponseDto.createResponseDto(user, review);
    }

    /**
     * 리뷰 검색 메서드(내가 작성한)
     *
     * @param user 로그인한 유저
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 검색된 리뷰 목록과 페이징 정보를 담은 응답 DTO
     * @since 2024-11-05
     */
    public PageResponseDto<List<ReviewResponseDto>> searchReviews(User user, int page, int size) {
        Page<Review> reviews = reviewRepository.findAllByUserIdOrderByUpdatedAtDesc(user.getId(), PageRequest.of(page, size));
        return createPageResponseDto(user, reviews);
    }

    /**
     * 가게 리뷰 검색 메서드(가게)
     *
     * @param user    로그인한 유저
     * @param storeId 가게 식별자
     * @param page    페이지 번호
     * @param size    페이지 크기
     * @return 검색된 리뷰 목록과 페이징 정보를 담은 응답 DTO
     * @since 2024-11-05
     */
    public PageResponseDto<List<ReviewResponseDto>> searchReviews(User user, Long storeId, int page, int size) {
        Page<Review> reviews = reviewRepository.findAllByStoreIdOrderByUpdatedAtDesc(storeId, PageRequest.of(page, size));
        return createPageResponseDto(user, reviews);
    }

    /**
     * 주어진 주문에 대한 리뷰를 조회합니다.
     *
     * @param user    로그인한 유저
     * @param orderId 조회할 리뷰가 속한 주문의 ID
     * @return 조회된 리뷰 정보를 담은 ReviewResponseDto 객체
     * @since 2024-11-05
     */
    public ReviewResponseDto findReview(User user, Long orderId) {
        Review review = reviewRepository.findByOrderIdOrderByUpdatedAtDesc(orderId);
        return ReviewResponseDto.createResponseDto(user, review);
    }

    /**
     * 리뷰를 수정합니다.
     *
     * @param user       로그인한 유저
     * @param requestDto 수정할 리뷰 정보
     * @return 수정된 리뷰 정보를 담은 ReviewResponseDto 객체
     * @since 2024-11-05
     */
    @Transactional
    public ReviewResponseDto updateReview(User user, Long reviewId, ReviewRequestDto requestDto) {
        Review review = findReview(reviewId);
        isMyReview(user.getId(), review.getId());
        review.update(requestDto);
        return ReviewResponseDto.createResponseDto(user, review);
    }

    /**
     * 특정 리뷰를 삭제합니다.
     *
     * @param user     로그인한 유저
     * @param reviewId 삭제할 리뷰의 ID
     * @return 삭제된 리뷰 정보를 담은 ReviewResponseDto 객체
     * @since 2024-11-05
     */
    public ReviewResponseDto deleteReview(User user, Long reviewId) {
        Review review = findReview(reviewId);
        isMyReview(user.getId(), review.getId());
        reviewRepository.delete(review);
        return ReviewResponseDto.createResponseDto(user, review);
    }

    /**
     * 주문 정보 조회 메서드
     *
     * @param orderId 주문 ID
     * @return 조회된 주문 정보
     * @throws ResponseException 주문이 존재하지 않거나 이미 처리된 경우 예외 발생
     * @since 2024-11-05
     */
    private Order findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_ORDER)
        );

        if (order.getState() != OrderState.COMPLETE)
            throw new ResponseException(ResponseCode.NOT_COMPLETE_ORDER);

        return order;
    }

    /**
     * 주어진 리뷰 ID로 리뷰를 조회합니다.
     *
     * @param reviewId 조회할 리뷰의 ID
     * @return 조회된 리뷰 객체
     * @throws ResponseException 리뷰가 존재하지 않을 경우 발생
     * @since 2024-11-05
     */
    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_REVIEW)
        );
    }

    /**
     * 주문에 대한 리뷰 존재 여부를 검사합니다.
     *
     * @param orderId 검사할 주문의 ID
     * @throws ResponseException 이미 리뷰가 존재하는 경우 발생
     * @since 2024-11-05
     */
    private void validateReviewForCreate(Long orderId) {
        if (reviewRepository.existsByOrderId(orderId))
            throw new ResponseException(ResponseCode.ALREADY_REVIEW);
    }

    /**
     * 현재 사용자가 리뷰의 작성자인지 검사합니다.
     *
     * @param userId  현재 사용자의 ID
     * @param orderId 리뷰가 속한 주문의 ID
     * @throws ResponseException 사용자 권한이 없을 경우 발생
     * @since 2024-11-05
     */
    private void isMyReview(Long userId, Long orderId) {
        if (!Objects.equals(userId, orderId))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    /**
     * 리뷰 목록 응답 DTO 생성 메서드
     *
     * @param user    현재 사용자 정보
     * @param reviews 리뷰 엔티티 페이지 정보
     * @return 리뷰 목록 응답 DTO (페이징 정보 포함)
     * @since 2024-11-05
     */
    private PageResponseDto<List<ReviewResponseDto>> createPageResponseDto(User user, Page<Review> reviews) {
        List<ReviewResponseDto> responseReviews = new ArrayList<>();
        for (Review review : reviews)
            responseReviews.add(ReviewResponseDto.createResponseDto(user, review));
        return PageResponseDto.of(responseReviews, reviews.getPageable(), reviews.getTotalPages());
    }
}
