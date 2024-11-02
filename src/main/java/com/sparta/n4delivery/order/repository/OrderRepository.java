package com.sparta.n4delivery.order.repository;

import com.sparta.n4delivery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
