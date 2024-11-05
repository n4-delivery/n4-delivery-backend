package com.sparta.n4delivery.menu.repository;

import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  List<Menu> findAllByIdIn(List<Long> menuIdList);
  boolean existsByStoreAndName(Store store, String name);
}
