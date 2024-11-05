package com.sparta.n4delivery.menu.entity;

import com.sparta.n4delivery.enums.MenuState;
import com.sparta.n4delivery.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @NotNull(message = "가게 정보는 필수입니다.")
    private Store store;

    @NotBlank(message = "메뉴 이름은 필수 입력 값입니다.")
    @Size(max = 100, message = "메뉴 이름은 100자 이내로 입력해 주세요.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    @Max(value = 100000, message = "가격은 100,000원 이하이어야 합니다.")
    @Positive(message = "가격은 양수여야 합니다.")
    @Column
    private Integer price;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "메뉴 상태는 필수 입력 값입니다.")
    @Builder.Default
    private MenuState state = MenuState.SALE;

    public void update(String name, Integer price, MenuState state) {
        this.name = name;
        this.price = price;
        this.state = state;

    }
}
