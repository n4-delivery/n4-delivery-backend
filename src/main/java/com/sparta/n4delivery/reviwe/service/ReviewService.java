package com.sparta.n4delivery.reviwe.service;

import com.sparta.n4delivery.common.dto.PageResponseDto;
import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.repository.OrderRepository;
import com.sparta.n4delivery.reviwe.dto.request.ReviewCreateRequestDto;
import com.sparta.n4delivery.reviwe.dto.response.ReviewResponseDto;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.reviwe.repository.ReviewRepository;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * @param req        HttpServletRequest 객체
     * @param storeId    리뷰를 남길 가게 식별자
     * @param orderId    리뷰를 남길 주문 식별자
     * @param requestDto 리뷰 생성 요청 DTO 객체
     * @return 생성된 리뷰 정보를 담은 응답 DTO 객체
     * @since 2024-11-05
     */
    public ReviewResponseDto createReview(HttpServletRequest req, Long storeId, Long orderId, ReviewCreateRequestDto requestDto) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).nickname("홍길동").build();
        Order order = findOrder(orderId);
        existReview(order);
        Review review = requestDto.convertDtoToEntity(user, order, storeId);
        reviewRepository.save(review);
        return ReviewResponseDto.createOrderResponseDto(user, review);
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
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResponseException(ResponseCode.NOT_FOUND_ORDER)
        );

        if (order.getState() != OrderState.COMPLETE)
            throw new ResponseException(ResponseCode.NOT_COMPLETE_ORDER);

        return order;
    }

    /**
     * 주문에 대한 리뷰 존재 여부 검사
     *
     * @param order 검사할 주문 객체
     * @throws ResponseException 리뷰가 이미 존재하는 경우 예외를 던집니다.
     * @since 2024-11-05
     */
    public void existReview(Order order) {
        if (reviewRepository.existsByOrder(order))
            throw new ResponseException(ResponseCode.ALREADY_REVIEW);
    }

    /**
     * 리뷰 검색 메서드(내가 작성한)
     *
     * @param req  HttpServletRequest 객체
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 검색된 리뷰 목록과 페이징 정보를 담은 응답 DTO
     * @since 2024-11-05
     */
    public PageResponseDto<List<ReviewResponseDto>> searchReviews(HttpServletRequest req, int page, int size) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).nickname("홍길동").build();
        Page<Review> reviews = reviewRepository.findAllByUserOrderByUpdatedAtDesc(user, PageRequest.of(page, size));
        return createPageResponseDto(user, reviews);
    }

    /**
     * 가게 리뷰 검색 메서드(가게)
     *
     * @param req     HttpServletRequest 객체
     * @param storeId 가게 식별자
     * @param page    페이지 번호
     * @param size    페이지 크기
     * @return 검색된 리뷰 목록과 페이징 정보를 담은 응답 DTO
     * @since 2024-11-05
     */
    public PageResponseDto<List<ReviewResponseDto>> searchReviews(HttpServletRequest req, Long storeId, int page, int size) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).nickname("홍길동").build();
        Store store = Store.builder().id(storeId).build();
        Page<Review> reviews = reviewRepository.findAllByStoreOrderByUpdatedAtDesc(store, PageRequest.of(page, size));
        return createPageResponseDto(user, reviews);
    }

    /**
     * 주어진 주문에 대한 리뷰를 조회합니다.
     *
     * @param req     HttpServletRequest 객체 (현재는 사용되지 않음)
     * @param orderId 조회할 리뷰가 속한 주문의 ID
     * @return 조회된 리뷰 정보를 담은 ReviewResponseDto 객체
     */
    public ReviewResponseDto findReview(HttpServletRequest req, Long orderId) {
        // TODO. khj cookie에서 얻어오는걸로 바꿔줄 것.
        User user = User.builder().id(1L).nickname("홍길동").build();
        Order order = Order.builder().id(orderId).build();
        Review review = reviewRepository.findByOrderOrderByUpdatedAtDesc(order);
        return ReviewResponseDto.createOrderResponseDto(user, review);
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
            responseReviews.add(ReviewResponseDto.createOrderResponseDto(user, review));
        return PageResponseDto.of(responseReviews, reviews.getPageable(), reviews.getTotalPages());
    }
}
