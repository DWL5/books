package com.suri.delivery.domain;

import com.suri.order.domain.Purchasing;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private String zipcode;


    @OneToOne(mappedBy = "delivery")
    private Purchasing purchasing;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public void setPurchasing(Purchasing purchasing) {
        this.purchasing = purchasing;
    }
}
