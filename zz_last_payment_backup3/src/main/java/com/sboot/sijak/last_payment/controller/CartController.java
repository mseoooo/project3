package com.sboot.sijak.last_payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sboot.sijak.last_payment.dto.CartDTO;
import com.sboot.sijak.last_payment.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

	@Autowired
	private CartService cartservice;

	@PostMapping("/cart/add")
	public String addToCart(@RequestParam String productName, @RequestParam String productPrice,
			@RequestParam String name, HttpSession session) {

		// CartDTO 객체 생성 및 데이터 설정
		CartDTO cartDTO = new CartDTO();
		cartDTO.setProductName(productName);
		cartDTO.setProductPrice(Integer.parseInt(productPrice));
		cartDTO.setName(name);

		// 장바구니에 상품 추가하는 로직
		cartservice.insertCart(cartDTO);

		// 장바구니 페이지로 리다이렉트
		return "redirect:/cart";
	}

	@PostMapping("/cart")
	public String viewCart(@RequestParam String name, Model model) {
		// 장바구니 정보 가져오기
		List<CartDTO> cartItems = cartservice.getCart(name);
		model.addAttribute("cartItems", cartItems); // 모델에 장바구니 정보 추가
		return "cart"; // cart.html로 이동
	}

	@GetMapping("/cart")
	public String viewCart(HttpSession session, Model model) {
		String name = (String) session.getAttribute("userId");

		// 장바구니 정보 가져오기
		List<CartDTO> cartItems = cartservice.getCart(name);
		model.addAttribute("cartItems", cartItems); // 모델에 장바구니 정보 추가

		return "cart"; // cart.html로 이동
	}

	 @PostMapping("/delete_cart")
	   public String deleteCart(@RequestParam int cartId, HttpSession session, Model model) {
	      // 장바구니 항목 삭제
	      cartservice.deleteCart(cartId);

	      // 장바구니 페이지로 리다이렉트
	      return "redirect:/cart";
	   }

}
