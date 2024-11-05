package com.sparta.n4delivery.reviwe.repository;

import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.reviwe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByOrder(Order order);
    boolean existsByOrder(Order order);
}
