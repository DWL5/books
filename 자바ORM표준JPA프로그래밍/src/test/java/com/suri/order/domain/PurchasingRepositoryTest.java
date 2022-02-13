package com.suri.order.domain;

import com.suri.delivery.domain.Delivery;
import com.suri.delivery.domain.DeliveryRepository;
import com.suri.member.domain.Member;
import com.suri.member.domain.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
class PurchasingRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void test() {
        Member sender = new Member("sender", "a", "b", "c");
        Member receiver = new Member("receiver", "a", "b", "c");
        Delivery delivery = new Delivery();
        Member saveSender = memberRepository.save(sender);
        Member saveReceiver = memberRepository.save(receiver);
        Delivery saveDelivery = deliveryRepository.save(delivery);

        Purchasing purchasing = new Purchasing(3);
        purchasing.setSender(saveSender);
        purchasing.setReceiver(saveReceiver);
        purchasing.setDelivery(saveDelivery);


        Purchasing savePurchasing = orderRepository.save(purchasing);

        Assertions.assertEquals("receiver", savePurchasing.getReceiver().getName());
        Assertions.assertEquals("sender", savePurchasing.getSender().getName());

        em.flush();
        em.clear();

        Purchasing saved = orderRepository.getById(savePurchasing.getId());
        Assertions.assertEquals("receiver", saved.getReceiver().getName());
        Assertions.assertEquals("sender", saved.getSender().getName());

        System.out.println("----------------------- getTest");
        Assertions.assertEquals(3, saved.getTest());

        Member test = new Member("test", "a", "b", "c");
        Member sTest = memberRepository.save(test);
        Assertions.assertEquals("test", sTest.getName());
    }
}