package com.sparta.n4delivery.order.repository;

import com.sparta.n4delivery.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 메뉴 JPA 레포지토리
 *
 * @since 2024-11-05
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
}
