package com.sparta.n4delivery.store.repository;

import com.sparta.n4delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
