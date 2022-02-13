package com.suri.member.domain;

import com.suri.common.BaseEntity;
import com.suri.order.domain.Purchasing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "receiver")
    List<Purchasing> purchasings = new ArrayList<>();

    public Member(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public void addOrder(Purchasing purchasing) {
        purchasings.add(purchasing);
    }

    public void removeOrder(Purchasing purchasing) {
        this.purchasings.remove(purchasing);
    }
}
