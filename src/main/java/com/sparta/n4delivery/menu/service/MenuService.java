package com.sparta.n4delivery.menu.service;

import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.menu.dto.MenuRequestDto;
import com.sparta.n4delivery.menu.dto.MenuResponseDto;
import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.menu.repository.MenuRepository;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.store.repository.StoreRepository;
import com.sparta.n4delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuResponseDto createMenu(User user, Long storeId, MenuRequestDto menuRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        if (!store.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        if (menuRepository.existsByStoreAndName(store, menuRequestDto.getName())) {
            throw new ResponseException(ResponseCode.ALREADY_MENU);
        }

        Menu menu = Menu.builder()
                .store(store)
                .name(menuRequestDto.getName())
                .price(menuRequestDto.getPrice())
                .build();

        menuRepository.save(menu);
        return MenuResponseDto.from(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(User user, Long storeId, Long menuId, MenuRequestDto menuRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_MENU));

        if (!store.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        if (menuRepository.existsByStoreAndNameAndIdNot(store, menuRequestDto.getName(), menuId)) {
            throw new ResponseException(ResponseCode.ALREADY_MENU);
        }

        menu.update(menuRequestDto.getName(), menuRequestDto.getPrice(), menuRequestDto.getState());

        return MenuResponseDto.from(menu);
    }

    @Transactional
    public void deleteMenu(User user, Long storeId, Long menuId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_STORE));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseException(ResponseCode.NOT_FOUND_MENU));

        if (!store.getUser().getId().equals(user.getId())) {
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
        }

        // 메뉴 삭제
        menu.softDelete();
    }
}