package com.sparta.n4delivery.user.repository;

import com.sparta.n4delivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
