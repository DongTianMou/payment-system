package com.dongtian.paymentsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongtian.paymentsystem.entity.PaymentInfo;
import com.dongtian.paymentsystem.sdk.AcpService;
import com.dongtian.paymentsystem.sdk.LogUtil;
import com.dongtian.paymentsystem.sdk.SDKConstants;
import com.dongtian.paymentsystem.service.CallbackService;
import com.dongtian.paymentsystem.service.PaymentInfoService;
import com.dongtian.paymentsystem.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;
@Service
@Slf4j
public class YinLianCallbackServiceImpl implements CallbackService {
    @Autowired
    private PaymentInfoService paymentInfoService;
    @Override
    public Map<String, String> synCallback(HttpServletRequest request) {
        LogUtil.writeLog("YinLianCallbackServiceImpl接收到前台通知开始");
        String encoding = request.getParameter( SDKConstants.param_encoding);
        Map<String, String> valideData = valideData(request, encoding);

        LogUtil.writeLog("YinLianCallbackServiceImpl接收到前台通知结束");
        return valideData;
    }

    private Map<String, String> valideData(HttpServletRequest req, String encoding) {
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        //日志输出
        LogUtil.printRequestLog(reqParam);

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                try {
                    value = new String(value.getBytes(encoding), encoding);
                } catch (Exception e2) {
                    // TODO: handle exception
                }
                valideData.put(key, value);
            }
        }
        return valideData;
    }

    private Map<String, String> getAllRequestParam(HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                // System.out.println("ServletUtil类247行 temp数据的键=="+en+"
                // 值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    @Transactional
    @Override
    public String asynCallback(HttpServletRequest request) {
        String encoding = request.getParameter(SDKConstants.param_encoding);
        Map<String, String> valideData = valideData(request, encoding);
        // 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            LogUtil.writeLog("[异步回调]--验证签名结果[失败].");
            // 验签失败，需解决验签问题
        }
        LogUtil.writeLog("[异步回调]--验证签名结果[成功].");
        // 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
        String orderId = valideData.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取

        ResultVO payInfoToken  = paymentInfoService.getPayInfoByOrderId(orderId);

        String json = new JSONObject().toJSONString(payInfoToken.getData());
        PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
        if (paymentInfo == null) {
            log.error("[异步回调]--查询paymentInfo信息失败");
            return "fail";
        }

        Integer state = paymentInfo.getState();
        if (state.equals(1)) {
            log.error("订单号:{},已经支付成功!,无需再次做操作..",orderId);
            return "ok";
        }
        // 第三方支付订单号
        paymentInfo.setPlatformorderId(valideData.get("queryId"));
        // 修改時間
        paymentInfo.setUpdate_time(new Date(  ) );
        // 支付报文
        paymentInfo.setPayMessage(new JSONObject().toJSONString(valideData));
        // 状态
        paymentInfo.setState(1);

        paymentInfoService.updatePayInfo(paymentInfo);
        // 金额 调用中石油充值支付接口--- 延迟 10 15
        // 异步 mq http协议
        return "ok";
    }
    
    
    
}
