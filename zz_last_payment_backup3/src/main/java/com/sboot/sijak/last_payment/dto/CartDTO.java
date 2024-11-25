package com.sboot.sijak.last_payment.dto;

import lombok.Data;

@Data
public class CartDTO {

	private int cart_ID;
	private String productName;
	private int productPrice;
	private String name;
	private String user_ID;
}
