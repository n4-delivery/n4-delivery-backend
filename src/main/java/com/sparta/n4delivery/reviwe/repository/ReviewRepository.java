package com.sparta.n4delivery.reviwe.repository;

import com.sparta.n4delivery.reviwe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
