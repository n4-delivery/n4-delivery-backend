package com.sparta.n4delivery.menu.service;

import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.menu.dto.MenuDeleteRequestDto;
import com.sparta.n4delivery.menu.dto.MenuRequestDto;
import com.sparta.n4delivery.menu.dto.MenuResponseDto;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.menu.repository.MenuRepository;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        if (!store.getUser().getId().equals(menuRequestDto.getUserId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        if (menuRepository.existsByStoreAndName(store, menuRequestDto.getName())) {
            throw new ResponseException(ResponseCode.ALREADY_MENU);
        }

        Menu menu = Menu.builder()
                .store(store)
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .state(menuRequestDto.getState())
                .build();

        menuRepository.save(menu);

        return MenuResponseDto.from(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long storeId, Long menuId, MenuRequestDto menuRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_MENU));

        if (!store.getUser().getId().equals(menuRequestDto.getUserId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        if (menuRepository.existsByStoreAndNameAndIdNot(store, menuRequestDto.getName(), menuId)) {
            throw new ResponseException(ResponseCode.ALREADY_MENU);
        }

        menu.update(menuRequestDto.getName(), menuRequestDto.getPrice(), menuRequestDto.getState());

        return MenuResponseDto.from(menu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId, MenuDeleteRequestDto deleteRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_MENU));

        if (!store.getUser().getId().equals(deleteRequestDto.getUserId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        // 메뉴 삭제
        menu.softDelete();
    }
}