package com.dongtian.paymentsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name="payment_type")
public class PaymentType {
	@Id
	private int typeId;
	/**
	 * 支付平台
	 */
	private String typeName;
	/**
	 * 同步通知
	 */
	private String frontUrl;
	/**
	 * 同步通知
	 */
	private String backUrl;
	/**
	 * 商户id
	 */
	private String merchantId;
	/**
	 * 创建时间
	 */
	private Date creat_time;
	/**
	 * 修改时间
	 */
	private Date update_time;
}