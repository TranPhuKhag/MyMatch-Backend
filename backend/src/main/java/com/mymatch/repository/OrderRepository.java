package com.mymatch.repository;

import com.mymatch.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByOrderCode(String orderCode);
    Order findByOrderCode(String orderCode);
}
