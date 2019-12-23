package com.dongtian.paymentsystem.service;

import com.dongtian.paymentsystem.entity.PaymentInfo;
import com.dongtian.paymentsystem.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentInfoService {

    ResultVO addPayInfoToken(@RequestBody PaymentInfo paymentInfo);

    ResultVO getPayInfoToken(@RequestParam("token") String token);

    ResultVO getPayInfoByOrderId(@RequestParam("orderId") String orderId);

    ResultVO updatePayInfo(@RequestBody PaymentInfo paymentInfo);


}
