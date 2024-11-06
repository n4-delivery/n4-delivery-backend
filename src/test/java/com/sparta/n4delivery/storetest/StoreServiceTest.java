package com.sparta.n4delivery.storetest;


import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.jwt.JwtUtil;
import com.sparta.n4delivery.store.dto.PaginatedStoreResponse;
import com.sparta.n4delivery.store.dto.ResponseStoreDto;
import com.sparta.n4delivery.store.dto.StoreDetailResponse;
import com.sparta.n4delivery.store.dto.StoreDto;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.store.service.StoreService;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

  @Mock
  private StoreRepository storeRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private StoreService storeService;

//  @Test
//  void testCreateStore_Success() {
//    User owner = new User();
//    owner.setId(1L);
//    owner.setEmail("owner@example.com");
//    owner.setType(UserType.OWNER);
//
//    StoreDto storeDto = new StoreDto("Test Store", "09:00", "22:00", 1000);
//
//    given(jwtUtil.extractUsername(anyString())).willReturn(owner.getEmail());
//    given(userRepository.findByEmail(owner.getEmail())).willReturn(Optional.of(owner));
//    given(storeRepository.findAllByUser(owner)).willReturn(List.of());
//    given(storeRepository.save(any(Store.class))).willReturn(Store.builder()
//        .id(1L)
//        .name("Test Store")
//        .openedAt(LocalTime.of(9, 0))
//        .closedAt(LocalTime.of(22, 0))
//        .minimumAmount(1000)
//        .state(StoreState.OPEN)
//        .user(owner)
//        .build());
//
//    ResponseStoreDto response = storeService.createStore("Bearer token", storeDto);
//
//    assertNotNull(response);
//    assertEquals("Test Store", response.getName());
//    assertEquals("09:00", response.getOpenedAt());
//    assertEquals("22:00", response.getClosedAt());
//    assertEquals(1000, response.getMinimumAmount());
//  }

//  @Test
//  void testUpdateStore_Success() {
//    User owner = new User();
//    owner.setId(1L);
//    owner.setEmail("owner@example.com");
//    owner.setType(UserType.OWNER);
//
//    Store store = Store.builder()
//        .id(1L)
//        .name("Test Store")
//        .openedAt(LocalTime.of(9, 0))
//        .closedAt(LocalTime.of(22, 0))
//        .minimumAmount(1000)
//        .state(StoreState.OPEN)
//        .user(owner)
//        .build();
//
//    StoreDto storeDto = new StoreDto("Updated Store", "08:00", "21:00", 2000);
//
//    given(jwtUtil.extractUsername(anyString())).willReturn(owner.getEmail());
//    given(userRepository.findByEmail(owner.getEmail())).willReturn(Optional.of(owner));
//    given(storeRepository.findById(1L)).willReturn(Optional.of(store));
//
//    ResponseStoreDto response = storeService.updateStore("Bearer token", 1L, storeDto);
//
//    assertNotNull(response);
//    assertEquals("Updated Store", response.getName());
//    assertEquals("08:00", response.getOpenedAt());
//    assertEquals("21:00", response.getClosedAt());
//    assertEquals(2000, response.getMinimumAmount());
//  }
//
//  @Test
//  void testGetStores_Success() {
//    Pageable pageable = PageRequest.of(0, 10);
//    List<Store> stores = List.of(
//        Store.builder()
//            .id(1L)
//            .name("Test Store")
//            .openedAt(LocalTime.of(9, 0))
//            .closedAt(LocalTime.of(22, 0))
//            .minimumAmount(1000)
//            .state(StoreState.OPEN)
//            .build()
//    );
//    Page<Store> page = new PageImpl<>(stores, pageable, 1);
//
//    given(storeRepository.findByNameContaining(anyString(), any(Pageable.class))).willReturn(page);
//
//    PaginatedStoreResponse response = storeService.getStores("Test", 1, 10);
//
//    assertNotNull(response);
//    assertEquals(1, response.getContents().size());
//    assertEquals("Test Store", response.getContents().get(0).getName());
//  }
//
//  @Test
//  void testGetStoreDetail_Success() {
//    Store store = Store.builder()
//        .id(1L)
//        .name("Test Store")
//        .openedAt(LocalTime.of(9, 0))
//        .closedAt(LocalTime.of(22, 0))
//        .minimumAmount(1000)
//        .state(StoreState.OPEN)
//        .build();
//
//    given(storeRepository.findById(1L)).willReturn(Optional.of(store));
//
//    StoreDetailResponse response = storeService.getStoreDetail(1L);
//
//    assertNotNull(response);
//    assertEquals("Test Store", response.getName());
//    assertEquals("09:00", response.getOpenedAt());
//    assertEquals("22:00", response.getClosedAt());
//    assertEquals(1000, response.getMinimumAmount());
//  }

  @Test
  void testDeleteStore_Success() {
    User owner = new User();
    owner.setId(1L);
    owner.setEmail("owner@example.com");
    owner.setType(UserType.OWNER);

    Store store = Store.builder()
        .id(1L)
        .name("Test Store")
        .openedAt(LocalTime.of(9, 0))
        .closedAt(LocalTime.of(22, 0))
        .minimumAmount(1000)
        .state(StoreState.OPEN)
        .user(owner)
        .build();

    given(jwtUtil.extractUsername(anyString())).willReturn(owner.getEmail());
    given(userRepository.findByEmail(owner.getEmail())).willReturn(Optional.of(owner));
    given(storeRepository.findById(1L)).willReturn(Optional.of(store));

    storeService.deleteStore("Bearer token", 1L);

    verify(storeRepository, times(1)).save(store);
    assertTrue(store.isDeleted());
  }
}