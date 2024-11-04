package com.sparta.n4delivery.store.service;


import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.exception.UnauthorizedException;
import com.sparta.n4delivery.jwt.JwtUtil;
import com.sparta.n4delivery.store.dto.ResponseStoreDto;
import com.sparta.n4delivery.store.dto.StoreDto;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import java.time.LocalTime;
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

  @Transactional
  public ResponseStoreDto createStore(String token, StoreDto storeDto) {
    // JWT 토큰에서 사용자 이메일을 추출
    String email = jwtUtil.extractUsername(token); // 사용자 이메일로 수정
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UnauthorizedException("사용자를 찾을 수 없습니다."));

    // 사장님 권한 확인
    if (!user.getType().equals(UserType.OWNER)) {
      throw new UnauthorizedException("사장님 권한을 가진 사용자만 가게를 생성할 수 있습니다.");
    }

    // 가게 수 제한 확인
    if (storeRepository.countByOwner(user) >= 3) {
      throw new IllegalStateException("사장님은 최대 3개의 가게만 운영할 수 있습니다.");
    }

    // 가게 생성 및 저장
    Store store = Store.builder()
        .name(storeDto.getName())
        .openedAt(LocalTime.parse(storeDto.getOpenedAt()))
        .closedAt(LocalTime.parse(storeDto.getClosedAt()))
        .minimumAmount(storeDto.getMinimumAmount())
        .state(StoreState.OPEN)
        .user(user)
        .build();

    storeRepository.save(store);

    // 생성된 Store 엔티티를 ResponseStoreDto로 변환하여 반환
    return new ResponseStoreDto(
        store.getId(),
        store.getName(),
        store.getOpenedAt().toString(),
        store.getClosedAt().toString(),
        store.getMinimumAmount(),
        store.getState().name()
    );
  }
}