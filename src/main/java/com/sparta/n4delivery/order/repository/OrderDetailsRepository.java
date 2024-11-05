package com.sparta.n4delivery.order.repository;

import com.sparta.n4delivery.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
}
