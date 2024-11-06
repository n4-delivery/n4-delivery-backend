package com.sparta.n4delivery.reviwe.dto.request;

import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @Size(min = 1, max = 5, message = "1~5사이의 별점을 입력해주세요.")
    private double score;
    private String comment;

    /**
     * 리뷰 DTO 객체를 Review 엔티티 객체로 변환합니다.
     *
     * @param user  주문자
     * @return 생성된 리뷰 엔티티 객체
     * @since 2024-11-05
     */
    public Review convertDtoToEntity(User user, Order order, Long storeId) {
        return Review.builder()
                .user(user)
                .store(Store.builder().id(storeId).build())
                .order(order)
                .score(score)
                .comment(comment)
                .build();
    }
}
