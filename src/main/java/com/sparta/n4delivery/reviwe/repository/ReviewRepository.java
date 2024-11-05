package com.sparta.n4delivery.reviwe.repository;

import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByOrder(Order order);

    boolean existsByOrder(Order order);

    Page<Review> findAllByUserOrderByUpdatedAtDesc(User user, Pageable pageable);

    Page<Review> findAllByStoreOrderByUpdatedAtDesc(Store user, Pageable pageable);

    Review findByOrderOrderByUpdatedAtDesc(Order order);
}
