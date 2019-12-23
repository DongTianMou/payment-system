package com.dongtian.paymentsystem.service.impl;

import com.dongtian.paymentsystem.dao.PaymentTypeDao;
import com.dongtian.paymentsystem.entity.PaymentType;
import com.dongtian.paymentsystem.service.PaymentTypeService;
import com.dongtian.paymentsystem.utils.ResultVOUtils;
import com.dongtian.paymentsystem.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {
    @Autowired
    private PaymentTypeDao paymentTypeDao;
    @Override
    public ResultVO getByTypeId(Integer id) {
        PaymentType paymentType = paymentTypeDao.getByTypeId( id );
        if (paymentType == null) {
            return ResultVOUtils.error(404,"未查找到类型");
        }
        return ResultVOUtils.success(paymentType);
    }
}
