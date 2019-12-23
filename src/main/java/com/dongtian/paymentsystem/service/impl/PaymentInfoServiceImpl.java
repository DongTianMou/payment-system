package com.dongtian.paymentsystem.service.impl;

import com.dongtian.paymentsystem.dao.PaymentInfoDao;
import com.dongtian.paymentsystem.entity.PaymentInfo;
import com.dongtian.paymentsystem.service.PaymentInfoService;
import com.dongtian.paymentsystem.utils.RedisUtils;
import com.dongtian.paymentsystem.utils.ResultVOUtils;
import com.dongtian.paymentsystem.utils.TokenUtils;
import com.dongtian.paymentsystem.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private PaymentInfoDao paymentInfoDao;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public ResultVO addPayInfoToken(PaymentInfo paymentInfo) {
        //添加paymentInfo到数据库
        PaymentInfo info = paymentInfoDao.save( paymentInfo );
        Integer infoId = info.getInfoId();
        //判断是否添加成功
        if (infoId==null){
            throw new RuntimeException( "数据库中没有该数据" );
        }
        //生成对应token
        String token = TokenUtils.getPayToken();
        //存放在redis中 key token value paymentInfo.id
        redisUtils.set( token,infoId+"",null );
        return ResultVOUtils.success( token );
    }

    @Override
    public ResultVO getPayInfoToken(String token) {
        // 判断token是否为空
        if (StringUtils.isEmpty( token )){
            throw new RuntimeException( "token为空" );
        }
        // 使用token去redis查找对应支付id
        String paymentInfoId = redisUtils.get( token );
        if (StringUtils.isEmpty( paymentInfoId )){
            throw new RuntimeException( "该支付信息不存在或者已过期" );
        }
        // 使用支付id查找数据库
        PaymentInfo paymentInfo = paymentInfoDao.getByInfoId( (Integer.parseInt(paymentInfoId) ));

        return ResultVOUtils.success( paymentInfo );
    }

    @Override
    public ResultVO getPayInfoByOrderId(String orderId) {
        //直接在数据库中查找
        if (StringUtils.isEmpty( orderId )){
            log.error("orderId为空，无法查询");
            throw new RuntimeException( "orderId为空，无法查询" );
        }
        PaymentInfo paymentInfo = paymentInfoDao.getByOrderId( orderId );
        return ResultVOUtils.success(paymentInfo);
    }

    @Override
    public ResultVO updatePayInfo(PaymentInfo paymentInfo) {
        if (paymentInfo==null){
            throw new RuntimeException( "paymentInfo为空" );
        }
        paymentInfoDao.save( paymentInfo );
        return ResultVOUtils.success();
    }
}
