package com.sparta.n4delivery.menu.repository;

import com.sparta.n4delivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
