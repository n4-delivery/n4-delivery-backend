package com.sparta.n4delivery.store.controller;

import com.sparta.n4delivery.login.LoginUser;
import com.sparta.n4delivery.menu.dto.MenuRequestDto;
import com.sparta.n4delivery.menu.dto.MenuResponseDto;
import com.sparta.n4delivery.menu.service.MenuService;
import com.sparta.n4delivery.store.dto.PaginatedStoreResponse;
import com.sparta.n4delivery.store.dto.ResponseStoreDto;
import com.sparta.n4delivery.store.dto.StoreDetailResponse;
import com.sparta.n4delivery.store.dto.StoreDto;
import com.sparta.n4delivery.store.service.StoreService;
import com.sparta.n4delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<ResponseStoreDto> createStore(
            @LoginUser User user,
            @RequestBody StoreDto storeDto) {
        ResponseStoreDto response = storeService.createStore(user, storeDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<MenuResponseDto> createMenu(
            @LoginUser User user,
            @PathVariable Long storeId,
            @RequestBody MenuRequestDto menuRequestDto
    ) {
        MenuResponseDto response = menuService.createMenu(user, storeId, menuRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @LoginUser User user,
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto menuRequestDto
    ) {
        MenuResponseDto response = menuService.updateMenu(user, storeId, menuId, menuRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @LoginUser User user,
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ) {
        menuService.deleteMenu(user, storeId, menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateStore(
            @LoginUser User user,
            @PathVariable Long storeId,
            @RequestBody StoreDto storeDto) {
        ResponseStoreDto response = storeService.updateStore(user, storeId, storeDto);
        return ResponseEntity.ok(response);
    }

    // 가게 다건 조회
    @GetMapping
    public ResponseEntity<PaginatedStoreResponse> getStores(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size) {
        PaginatedStoreResponse response = storeService.getStores(name, page, size);
        return ResponseEntity.ok(response);
    }

    // 가게 단건 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDetailResponse> getStoreDetail(@PathVariable Long storeId) {
        StoreDetailResponse response = storeService.getStoreDetail(storeId);
        return ResponseEntity.ok(response);
    }

    // 가게 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(
            @LoginUser User user,
            @PathVariable Long storeId) {
        storeService.deleteStore(user, storeId);
        return ResponseEntity.noContent().build();
    }
}
