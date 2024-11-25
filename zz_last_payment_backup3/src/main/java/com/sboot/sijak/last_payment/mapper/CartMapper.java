package com.sboot.sijak.last_payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sboot.sijak.last_payment.dto.CartDTO;

@Mapper
public interface CartMapper {

	void insertCart(CartDTO cartdto);

	List<CartDTO> getCart(String name);

	void deleteCart(int cart_ID);
}
