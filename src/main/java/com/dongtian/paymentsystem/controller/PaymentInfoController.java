package com.dongtian.paymentsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongtian.paymentsystem.entity.PaymentInfo;
import com.dongtian.paymentsystem.service.PayAdaptService;
import com.dongtian.paymentsystem.service.PayService;
import com.dongtian.paymentsystem.service.PaymentInfoService;
import com.dongtian.paymentsystem.utils.RedisUtils;
import com.dongtian.paymentsystem.utils.ResultVOUtils;
import com.dongtian.paymentsystem.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@RestController
@RequestMapping("/pay")
public class PaymentInfoController {
    @Autowired
    private PaymentInfoService paymentInfoService;
    @Autowired
    private PayService payService;
    @GetMapping("/addPayInfoToken")
    public ResultVO addPayInfoToken(){
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setInfoId( 2 );
        paymentInfo.setTypeId(1);
        paymentInfo.setOrderId( "123456" );
        paymentInfo.setPrice( 200 );
        paymentInfo.setState(0);
        paymentInfo.setPlatformorderId( "1" );
        paymentInfo.setPayMessage( "1" );
        paymentInfo.setSource( "1" );
        paymentInfo.setCreat_time( new Date(  ) );
        paymentInfo.setUpdate_time( new Date(  ) );
        ResultVO resultVO = paymentInfoService.addPayInfoToken(paymentInfo );
        return ResultVOUtils.success(resultVO.getData());
    }

    @GetMapping("/getPayInfoToken")
    public ResultVO  getPayInfoToken(String token){
        ResultVO resultVO = paymentInfoService.getPayInfoToken(token);
        return ResultVOUtils.success(resultVO);
    }

    @RequestMapping("/payGateway")
    public void payGateway(HttpServletRequest request, String token, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        ResultVO payInfoToken = paymentInfoService.getPayInfoToken(token);

        PrintWriter out=resp.getWriter();
        if (payInfoToken.getData() == null) {
            resp.getWriter().write("系统错误，PaymentInfo为空!");
            return ;
        }
        String json = new JSONObject().toJSONString(payInfoToken.getData());
        PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
        String html = payService.pay(paymentInfo);
        out.println(html);
        out.close();
    }

}
