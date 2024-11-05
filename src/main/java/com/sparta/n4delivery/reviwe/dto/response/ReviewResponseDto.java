package com.sparta.n4delivery.reviwe.dto.response;

import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 리뷰 정보를 담는 응답 DTO 클래스
 *
 * @since 2024-11-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private double score;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 리뷰 정보를 담은 응답 DTO 객체 생성
     *
     * @param user   유저 엔티티
     * @param review 리뷰 엔티티
     * @return 생성된 리뷰 응답 DTO 객체
     * @since 2024-11-05
     */
    public static ReviewResponseDto createResponseDto(User user, Review review) {
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.id = review.getId();
        responseDto.userId = user.getId();
        responseDto.userName = user.getNickname();
        responseDto.score = review.getScore();
        responseDto.comment = review.getComment();
        responseDto.createdAt = review.getCreatedAt();
        responseDto.updatedAt = review.getUpdatedAt();
        return responseDto;
    }
}
