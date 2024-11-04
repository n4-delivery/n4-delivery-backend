package com.sparta.n4delivery.menu.repository;

import com.sparta.n4delivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByIdIn(List<Long> menuIdList);
}
