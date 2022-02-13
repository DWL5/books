package com.suri.order.domain;

import com.suri.common.BaseEntity;
import com.suri.delivery.domain.Delivery;
import com.suri.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Purchasing extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @OneToMany(mappedBy = "purchasing", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="delivery_id")
    private Delivery delivery;

    public Purchasing(int test) {
        this.test = test;
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        orderItems.add(orderItem);
        orderItem.setPurchasing(this);
    }

    public void setSender(Member member) {

        if (this.sender != null) {
            this.sender.removeOrder(this);
        }

        this.sender = member;
        member.addOrder(this);
    }

    public void setReceiver(Member member) {

        if (this.receiver != null) {
            this.receiver.removeOrder(this);
        }

        this.receiver = member;
        member.addOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setPurchasing(this);
    }

    public Long getId() {
        return id;
    }
}
