package com.sparta.n4delivery.user.entity;

import com.sparta.n4delivery.common.entity.Timestamped;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * 회원 정보를 담는 Entity 클래스
 *
 * @since 2024-10-03
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserType type = UserType.USER;

    @Column(updatable = false)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @Builder.Default
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
