package com.dongtian.paymentsystem.controller;

import com.dongtian.paymentsystem.sdk.AcpService;
import com.dongtian.paymentsystem.sdk.LogUtil;
import com.dongtian.paymentsystem.sdk.SDKConstants;
import com.dongtian.paymentsystem.service.CallbackService;
import com.dongtian.paymentsystem.utils.ResultVOUtils;
import com.dongtian.paymentsystem.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/pay/callback")
@RestController
public class YinLianCallbackController {
    @Autowired
    private CallbackService callbackService;

    private static final String PAY_SUCCESS = "pay_success";
    private static final String PAY_FAIL = "pay_fail";

    @RequestMapping("/syn")
    public ResultVO synCallback(HttpServletRequest req) {
        Map<String, String> valideData = callbackService.synCallback(req);
        // 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        String encoding = req.getParameter( SDKConstants.param_encoding);
        if (!AcpService.validate(valideData, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            // 验签失败，需解决验签问题
            //return PAY_FAIL;
            return ResultVOUtils.error( 404,"验证前面失败" );
        }
        req.setAttribute("txnAmt", Double.parseDouble(valideData.get("txnAmt")) / 100);
        req.setAttribute("orderId", Long.parseLong(valideData.get("orderId")));
        LogUtil.writeLog("验证签名结果[成功].");
        //return PAY_SUCCESS;
        return ResultVOUtils.success( "验证签名结果[成功]" );
    }

    @RequestMapping("/asyn")
    public String asynCallback(HttpServletRequest req) {
        return callbackService.asynCallback(req);
    }
}