package com.sparta.n4delivery.order.dto.request;

import com.sparta.n4delivery.menu.entity.Menu;
import com.sparta.n4delivery.order.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCreateOrderDetailDto {
    private Long menuId;
    private Integer count;

    /**
     * DTO 객체를 엔티티 객체로 변환
     *
     * @param menu  메뉴 정보
     * @return 생성된 댓글 엔티티 객체
     * @since 2024-10-18
     */
    public OrderDetail convertDtoToEntity(Menu menu) {
        return OrderDetail.builder()
                .menu(menu)
                .count(count)
                .price(menu.getPrice() * count)
                .build();
    }
}
