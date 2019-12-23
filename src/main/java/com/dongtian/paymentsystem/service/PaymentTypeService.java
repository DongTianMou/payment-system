package com.dongtian.paymentsystem.service;

import com.dongtian.paymentsystem.vo.ResultVO;

public interface PaymentTypeService {
    ResultVO getByTypeId(Integer id);
}
