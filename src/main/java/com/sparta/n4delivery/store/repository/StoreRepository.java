package com.sparta.n4delivery.store.repository;

import com.sparta.n4delivery.store.entity.Store;

import com.sparta.n4delivery.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
  List<Store> findAllByUser(User user);
  Page<Store> findByNameContaining(String name, Pageable pageable);
  Page<Store> findByNameContainingAndDeletedAtIsNull(String name, Pageable pageable);
  Optional<Store> findByIdAndDeletedAtIsNull(Long id);
}
