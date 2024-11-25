package com.sboot.sijak.last_payment.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentDTO {

	private String productName;
	private int productPrice;
	private String merchantUid;
	private String buyerName;
	private String buyerTel;
	private Date purchaseDate;

	private String id;
	private String name;
	private Date birth;
	private String tel;
	private String email;
}
