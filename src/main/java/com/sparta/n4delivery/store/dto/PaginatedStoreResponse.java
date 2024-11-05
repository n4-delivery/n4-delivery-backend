package com.sparta.n4delivery.store.dto;

import java.util.List;
import lombok.Data;

@Data
public class PaginatedStoreResponse {
  private List<StoreSummaryResponse> contents;
  private int page;
  private int size;
  private int totalPage;

  public PaginatedStoreResponse(List<StoreSummaryResponse> contents, int page, int size, int totalPage) {
    this.contents = contents;
    this.page = page;
    this.size = size;
    this.totalPage = totalPage;
  }
}