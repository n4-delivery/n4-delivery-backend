package com.sparta.n4delivery.order.entity;

import com.sparta.n4delivery.common.entity.Timestamped;
import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Setter
    @Column
    private Integer totalPrice;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private OrderState state = OrderState.REQUEST;

    @OneToMany(mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @Builder.Default
    private List<OrderDetails> orderDetails = new ArrayList<>();
}
