package com.sparta.n4delivery.order.entity;

import com.sparta.n4delivery.common.entity.Timestamped;
import com.sparta.n4delivery.enums.OrderState;
import com.sparta.n4delivery.enums.ResponseCode;
import com.sparta.n4delivery.exception.ResponseException;
import com.sparta.n4delivery.store.entity.Store;
import com.sparta.n4delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문에 대한 엔티티 클래스
 *
 * @since 2024-11-05
 */
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
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void updateState(OrderState state) {
        if (this.state != OrderState.REQUEST && state == OrderState.CANCEL)
            throw new ResponseException(ResponseCode.ALREADY_ACCEPT_ORDER);

        if(this.state == OrderState.COMPLETE)
            throw new ResponseException(ResponseCode.ALREADY_COMPLETE_ORDER);

        this.state = state;
    }
}
