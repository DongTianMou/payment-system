package com.dongtian.paymentsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name="payment_info")
public class PaymentInfo{
	@Id
	private Integer infoId;
	/**
	 * 支付类型
	 */
	private Integer typeId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 第三方平台支付id
	 */
	private String platformorderId;
	/**
	 * 价格 以分为单位
	 */
	private Integer price;
	/**
	 * 支付来源
	 */
	private String source;
	/**
	 * 支付状态
	 */
	private Integer state;
	/**
	 * 支付报文
	 */
	private String payMessage;

	/**
	 * 创建时间
	 */
	private Date creat_time;
	/**
	 * 修改时间
	 */
	private Date update_time;
}
