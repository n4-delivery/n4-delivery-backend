package com.sparta.n4delivery.store.controller;

import com.sparta.n4delivery.menu.dto.MenuDeleteRequestDto;
import com.sparta.n4delivery.menu.dto.MenuRequestDto;
import com.sparta.n4delivery.menu.dto.MenuResponseDto;
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
    public ResponseEntity<MenuResponseDto> createMenu(
            @PathVariable Long storeId,
            @RequestBody MenuRequestDto menuRequestDto
    ) {
        menuService.createMenu(storeId, menuRequestDto);
        MenuResponseDto response = new MenuResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto menuRequestDto
    ) {
        MenuResponseDto response = menuService.updateMenu(storeId, menuId, menuRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuDeleteRequestDto deleteRequestDto
    ) {
        menuService.deleteMenu(storeId, menuId, deleteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
