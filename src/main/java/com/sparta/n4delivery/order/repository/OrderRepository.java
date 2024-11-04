package com.sparta.n4delivery.order.repository;

import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 JPA 레포지토리
 *
 * @since 2024-11-05
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserOrderByUpdatedAtDesc(User user, Pageable pageable);
    Page<Order> findAllByStoreOrderByUpdatedAtDesc(Store store, Pageable pageable);
}
