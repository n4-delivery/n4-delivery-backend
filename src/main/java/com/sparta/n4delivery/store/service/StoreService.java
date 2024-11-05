package com.sparta.n4delivery.store.service;



import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.jwt.JwtUtil;
import com.sparta.n4delivery.menu.dto.MenuResponseDto;
import com.sparta.n4delivery.store.dto.PaginatedStoreResponse;
import com.sparta.n4delivery.store.dto.ResponseStoreDto;
import com.sparta.n4delivery.store.dto.StoreDetailResponse;
import com.sparta.n4delivery.store.dto.StoreDto;
import com.sparta.n4delivery.store.dto.StoreSummaryResponse;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {

  private final StoreRepository storeRepository;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public StoreService(StoreRepository storeRepository, UserRepository userRepository, JwtUtil jwtUtil) {
    this.storeRepository = storeRepository;
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

//  //가게 수정 메서드
//  @Transactional
//  public ResponseStoreDto updateStore(String token, Long storeId, StoreDto storeDto) {
//    // JWT 토큰에서 사용자 이메일을 추출
//    String email = jwtUtil.extractUsername(token);
//    User user = userRepository.findByEmail(email)
//        .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_USER));
//
//    // 가게 조회 및 사용자 소유 확인
//    Store store = storeRepository.findById(storeId)
//        .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));
//    if (!store.getUser().equals(user)) {
//      throw new ResponseException(ResponseCode.INVALID_PERMISSION);
//    }
//
//    // 가게 정보 업데이트
//    store.setName(storeDto.getName());
//    store.setOpenedAt(LocalTime.parse(storeDto.getOpenedAt()));
//    store.setClosedAt(LocalTime.parse(storeDto.getClosedAt()));
//    store.setMinimumAmount(storeDto.getMinimumAmount());
//
//    // 업데이트된 Store 엔티티를 ResponseStoreDto로 변환하여 반환
//    return new ResponseStoreDto(
//        store.getId(),
//        store.getName(),
//        store.getOpenedAt().toString(),
//        store.getClosedAt().toString(),
//        store.getMinimumAmount(),
//        store.getState().name()
//    );
//  }

//  // 다건 조회 메서드
//  public PaginatedStoreResponse getStores(String name, int page, int size) {
//    PageRequest pageRequest = PageRequest.of(page - 1, size);
//    Page<Store> stores = storeRepository.findByNameContaining(name, pageRequest);
//
//    List<StoreSummaryResponse> storeContents = stores.getContent().stream()
//        .map(store -> new StoreSummaryResponse(
//            store.getId(),
//            store.getName(),
//            store.getOpenedAt().toString(),
//            store.getClosedAt().toString(),
//            store.getMinimumAmount(),
//            store.getState().name()
//        )).collect(Collectors.toList());
//
//    return new PaginatedStoreResponse(
//        storeContents,
//        page,
//        size,
//        stores.getTotalPages()
//    );
//  }
//
//  // 단건 조회 메서드
//  public StoreDetailResponse getStoreDetail(Long storeId) {
//    Store store = storeRepository.findById(storeId)
//        .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));
//
//    return new StoreDetailResponse(
//        store.getId(),
//        store.getName(),
//        store.getOpenedAt().toString(),
//        store.getClosedAt().toString(),
//        store.getMinimumAmount(),
//        store.getState().name(),
//        store.getMenus().stream()
//            .map(menu -> new MenuResponseDto(menu.getId(), menu.getName(), menu.getPrice()))
//            .collect(Collectors.toList())
//    );
//  }

  @Transactional
  public void deleteStore(String token, Long storeId) {
    // JWT 토큰에서 사용자 이메일을 추출
    String email = jwtUtil.extractUsername(token);
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_USER));

    // 가게 조회 및 소유자 확인
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));
    if (!store.getUser().equals(user)) {
      throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    // 가게 soft delete 처리
    store.softDelete();
    storeRepository.save(store);
  }
}