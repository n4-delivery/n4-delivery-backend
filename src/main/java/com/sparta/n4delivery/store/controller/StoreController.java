package com.sparta.n4delivery.store.controller;


import com.sparta.n4delivery.store.dto.PaginatedStoreResponse;
import com.sparta.n4delivery.store.dto.ResponseStoreDto;
import com.sparta.n4delivery.store.dto.StoreDetailResponse;
import com.sparta.n4delivery.store.dto.StoreDto;
import com.sparta.n4delivery.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//  @PutMapping("/{storeId}")
//  public ResponseEntity<?> updateStore(
//      @RequestHeader("Authorization") String authorizationHeader,
//      @PathVariable Long storeId,
//      @RequestBody StoreDto storeDto) {
//    String token = authorizationHeader.replace("Bearer ", "");
//    ResponseStoreDto response = storeService.updateStore(token, storeId, storeDto);
//    return ResponseEntity.ok(response);
//  }
//
//  // 가게 다건 조회
//  @GetMapping
//  public ResponseEntity<PaginatedStoreResponse> getStores(
//      @RequestParam String name,
//      @RequestParam int page,
//      @RequestParam int size) {
//    PaginatedStoreResponse response = storeService.getStores(name, page, size);
//    return ResponseEntity.ok(response);
//  }
//
//  // 가게 단건 조회
//  @GetMapping("/{storeId}")
//  public ResponseEntity<StoreDetailResponse> getStoreDetail(@PathVariable Long storeId) {
//    StoreDetailResponse response = storeService.getStoreDetail(storeId);
//    return ResponseEntity.ok(response);
//  }
//
//  // 가게 삭제
//  @DeleteMapping("/{storeId}")
//  public ResponseEntity<?> deleteStore(
//      @RequestHeader("Authorization") String authorizationHeader,
//      @PathVariable Long storeId) {
//    String token = authorizationHeader.replace("Bearer ", "");
//    storeService.deleteStore(token, storeId);
//    return ResponseEntity.noContent().build();
//  }
}
