package com.dongtian.paymentsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongtian.paymentsystem.entity.PaymentInfo;
import com.dongtian.paymentsystem.entity.PaymentType;
import com.dongtian.paymentsystem.service.PayAdaptService;
import com.dongtian.paymentsystem.service.PayService;
import com.dongtian.paymentsystem.service.PaymentTypeService;
import com.dongtian.paymentsystem.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private YinLianPay yinLianPay;
    @Override
    public String pay(PaymentInfo paymentInfo) {
        //拿到支付类型
        Integer typeId = paymentInfo.getTypeId();
        ResultVO resultVO = paymentTypeService.getByTypeId( typeId );
        String json = new JSONObject().toJSONString(resultVO.getData());
        PaymentType paymentType = new JSONObject().parseObject(json, PaymentType.class);
        if (paymentType == null) {
            return null;
        }
        String typeName = paymentType.getTypeName();
        PayAdaptService payAdaptService=null;
        switch (typeName) {
            case "yinlianPay":
                payAdaptService = yinLianPay;
                break;

            default:
                break;
        }
        return payAdaptService.pay(paymentInfo, paymentType);
    }
}
