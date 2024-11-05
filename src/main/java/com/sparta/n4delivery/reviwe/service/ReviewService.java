package com.sparta.n4delivery.reviwe.service;

import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.order.repository.OrderRepository;
import com.sparta.n4delivery.reviwe.dto.request.ReviewCreateRequestDto;
import com.sparta.n4delivery.reviwe.dto.response.ReviewResponseDto;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.reviwe.repository.ReviewRepository;
import com.sparta.n4delivery.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
