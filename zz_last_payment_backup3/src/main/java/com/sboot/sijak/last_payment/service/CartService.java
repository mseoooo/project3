package com.sboot.sijak.last_payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.sijak.last_payment.dto.CartDTO;
import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.mapper.CartMapper;

@Service
public class CartService {

	@Autowired
	private CartMapper cartmapper;

	public void insertCart(CartDTO cartdto) {
		cartmapper.insertCart(cartdto);
	}

	public List<CartDTO> getCart(String name) {
		return cartmapper.getCart(name);
	}

	public void deleteCart(int cart_ID) {
		cartmapper.deleteCart(cart_ID);
	}
}
