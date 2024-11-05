package com.sparta.n4delivery.reviwe.repository;

import com.sparta.n4delivery.reviwe.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);

    Page<Review> findAllByUserIdOrderByUpdatedAtDesc(Long userId, Pageable pageable);

    Page<Review> findAllByStoreIdOrderByUpdatedAtDesc(Long storeId, Pageable pageable);

    Review findByOrderIdOrderByUpdatedAtDesc(Long orderId);
}
