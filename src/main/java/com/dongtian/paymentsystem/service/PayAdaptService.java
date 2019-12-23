package com.dongtian.paymentsystem.service;

import com.dongtian.paymentsystem.entity.PaymentInfo;
import com.dongtian.paymentsystem.entity.PaymentType;

public interface PayAdaptService {
    String pay(PaymentInfo paymentInfo, PaymentType paymentType );
}
