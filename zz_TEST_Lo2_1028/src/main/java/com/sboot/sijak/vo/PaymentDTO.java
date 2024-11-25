package com.sboot.sijak.vo;

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
}
