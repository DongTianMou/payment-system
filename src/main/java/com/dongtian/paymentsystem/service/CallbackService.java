package com.dongtian.paymentsystem.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CallbackService {
     /*
     *同步回调
     */
    Map<String, String> synCallback(HttpServletRequest request);

    /**
     * 异步回调
     */
    String asynCallback(HttpServletRequest request);
}
