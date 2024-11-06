package com.sparta.n4delivery.reviwe.entity;

import com.sparta.n4delivery.common.entity.Timestamped;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.reviwe.dto.request.ReviewRequestDto;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column
    @ColumnDefault("3")
    private Double score;

    @Column(length = 300)
    private String comment;

    public void update(ReviewRequestDto requestDto) {
        score = requestDto.getScore();
        comment = requestDto.getComment();
    }
}
