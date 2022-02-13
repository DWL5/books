package com.suri.order.domain;

import com.suri.order.domain.item.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Purchasing purchasing;

    private int orderPrice;
    private int count;

    public void setPurchasing(Purchasing purchasing) {
        this.purchasing = purchasing;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
