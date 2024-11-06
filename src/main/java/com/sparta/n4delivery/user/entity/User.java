package com.sparta.n4delivery.user.entity;

import com.sparta.n4delivery.common.entity.Timestamped;
import com.sparta.n4delivery.enums.UserType;
import com.sparta.n4delivery.order.entity.Order;
import com.sparta.n4delivery.reviwe.entity.Review;
import com.sparta.n4delivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String userName;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserType type = UserType.USER;

    @Column
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

    public void delete() {
        deletedAt = LocalDateTime.now();
    }
}
