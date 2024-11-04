package com.sparta.n4delivery.store.controller;

import com.sparta.n4delivery.menu.dto.MenuRequestDto;
import com.sparta.n4delivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
    private final MenuService menuService;

    @PostMapping("/{storeId}/menu")
    public ResponseEntity<String> createMenu(
            @PathVariable Long storeId,
            @RequestBody MenuRequestDto menuRequestDto
            ) {

        menuService.createMenu(storeId, menuRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("메뉴가 생성되었습니다.");
    }

}
