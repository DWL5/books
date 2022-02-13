package com.suri.order.domain;

import com.suri.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Purchasing, Long> {
}
