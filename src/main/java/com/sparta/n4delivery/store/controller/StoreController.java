package com.sparta.n4delivery.store.controller;


import com.sparta.n4delivery.store.dto.ResponseStoreDto;
import com.sparta.n4delivery.store.dto.StoreDto;
import com.sparta.n4delivery.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

  private final StoreService storeService;

  public StoreController(StoreService storeService) {
    this.storeService = storeService;
  }

  @PostMapping
  public ResponseEntity<ResponseStoreDto> createStore(
      @RequestHeader("Authorization") String authorizationHeader,
      @RequestBody StoreDto storeDto) {
    String token = authorizationHeader.replace("Bearer ", "");
    ResponseStoreDto response = storeService.createStore(token, storeDto);
    return ResponseEntity.ok(response);
  }
}
