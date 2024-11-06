package com.sparta.n4delivery.store.service;


import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.enums.StoreState;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.common.util.JwtUtil;
import com.sparta.n4delivery.menu.dto.MenuResponseDto;
import com.sparta.n4delivery.store.dto.*;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import com.sparta.n4delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseStoreDto createStore(User user, StoreDto storeDto) {
        // 사장님 권한 확인
        if (!user.getType().equals(UserType.OWNER)) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        // 가게 수 제한 확인
        List<Store> userStores = storeRepository.findAllByUser(user);
        if (userStores.size() >= 3) {
            throw new ResponseException(ResponseCode.LIMIT_STORE);
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

    //가게 수정 메서드
    @Transactional
    public ResponseStoreDto updateStore(User user, Long storeId, StoreDto storeDto) {
        // 가게 조회 및 사용자 소유 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));
        if (!store.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        // 가게 정보 업데이트
        store.update(storeDto);

        // 업데이트된 Store 엔티티를 ResponseStoreDto로 변환하여 반환
        return new ResponseStoreDto(
                store.getId(),
                store.getName(),
                store.getOpenedAt().toString(),
                store.getClosedAt().toString(),
                store.getMinimumAmount(),
                store.getState().name()
        );
    }

    // 다건 조회 메서드
    public PaginatedStoreResponse getStores(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Store> stores = storeRepository.findByNameContaining(name, pageRequest);

        List<StoreSummaryResponse> storeContents = stores.getContent().stream()
                .map(store -> new StoreSummaryResponse(
                        store.getId(),
                        store.getName(),
                        store.getOpenedAt().toString(),
                        store.getClosedAt().toString(),
                        store.getMinimumAmount(),
                        store.getState().name()
                )).collect(Collectors.toList());

        return new PaginatedStoreResponse(
                storeContents,
                page,
                size,
                stores.getTotalPages()
        );
    }

    // 단건 조회 메서드
    public StoreDetailResponse getStoreDetail(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getOpenedAt().toString(),
                store.getClosedAt().toString(),
                store.getMinimumAmount(),
                store.getState().name(),
                store.getMenus().stream()
                        .map(menu -> new MenuResponseDto(menu.getId(), menu.getName(), menu.getPrice()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void deleteStore(User user, Long storeId) {
        // 가게 조회 및 소유자 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));
        if (!store.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        // 가게 soft delete 처리
        store.softDelete();
        storeRepository.save(store);
    }
}