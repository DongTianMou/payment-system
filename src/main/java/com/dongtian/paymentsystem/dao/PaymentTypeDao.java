package com.dongtian.paymentsystem.dao;

import com.dongtian.paymentsystem.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PaymentTypeDao extends JpaRepository<PaymentType,Integer> {
    PaymentType getByTypeId(Integer id);
}
