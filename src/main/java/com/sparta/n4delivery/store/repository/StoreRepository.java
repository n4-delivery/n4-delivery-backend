package com.sparta.n4delivery.store.repository;

import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
  Integer countByOwner(User owner);
}
