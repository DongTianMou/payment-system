package com.dongtian.paymentsystem.dao;

import com.dongtian.paymentsystem.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PaymentInfoDao extends JpaRepository<PaymentInfo,Integer> {
    //通过id查询信息
    PaymentInfo getByInfoId(Integer infoId);
    //通过oderId查询
    PaymentInfo getByOrderId(String orderId);

}
