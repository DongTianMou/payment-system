package com.dongtian.paymentsystem.utils;

import java.util.UUID;

public class TokenUtils {

	public static String getToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取支付token
	 * @return
	 */
	public static String getPayToken() {
		return "pay-" + UUID.randomUUID().toString();
	}
}
