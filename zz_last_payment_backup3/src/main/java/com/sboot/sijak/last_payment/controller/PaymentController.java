package com.sboot.sijak.last_payment.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.jwtutil.JwtUtil;
import com.sboot.sijak.last_payment.service.CartService;
import com.sboot.sijak.last_payment.service.PaymentService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8810, http://localhost:8080, http://localhost:8030,http://localhost:8761,http://localhost:8088", allowCredentials = "true")
@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private CartService cartservice;

	@Autowired
	private HttpSession session;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping("/")
	public String goMain() {
		return "redirect:http://localhost:8810/";
	}

	@RequestMapping("/trip")
	public String goTrip() {
		return "redirect:http://localhost:8080/trip"; 
	}

	@RequestMapping("/shop")
	public String shop(HttpServletRequest request, Model model) {

		String jwtToken = null;
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					jwtToken = cookie.getValue(); // 쿠키에서 jwtToken 값을 가져옵니다.
					break;
				}
			}
		}

		// JWT 토큰에서 사용자 ID를 추출
		String userId = jwtUtil.getUserIdFromToken(jwtToken);
		String userName = jwtUtil.getUserNameFromToken(jwtToken);
		// ID가 유효하지 않다면 로그인 페이지로 리디렉션
		if (userId == null) {
			return "redirect:/kaja/login";
		}

		// 세션에 사용자 ID를 저장
		session.setAttribute("userId", userId);
		session.setAttribute("userName", userName);
		// 모델에 사용자 ID를 추가 (필요 시 화면에서 사용)
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		// shop 페이지로 이동
		return "shophome"; // 실제로는 shop 페이지 템플릿을 반환
	}

	@RequestMapping("/mypage")
	public String goMypage() {
		return "redirect:http://localhost:8810/mypage";
	}

	@GetMapping("/asd")
	public String fsdf() {
		return "pay_end";
	}

	@GetMapping("/payment/cart")
	public String gocart() {
		return "cart";
	} // 나중에 화면 정해지면 지우기 실행 용도로 적어놈

	@PostMapping("/product")
	public String movePaymentPage(@RequestParam String productName, @RequestParam String productPrice,
			@RequestParam String image, Model model) {

		model.addAttribute("productName", productName);
		model.addAttribute("productPrice", productPrice);
		model.addAttribute("image", image);
		return "product";
	}

	@PostMapping("/success-page")
	public String handlePaymentSuccess(@RequestParam String merchant_uid, @RequestParam String buyer_name,
			@RequestParam String buyer_tel, @RequestParam String product_name, @RequestParam String product_price,
			@RequestParam(required = false) Integer cart_id, HttpSession session, Model model) {

		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setMerchantUid(merchant_uid);
		paymentDTO.setBuyerName(buyer_name);
		paymentDTO.setBuyerTel(buyer_tel);
		paymentDTO.setProductName(product_name);
		paymentDTO.setProductPrice(Integer.parseInt(product_price)); // 문자열을 정수로 변환
		paymentDTO.setPurchaseDate(new Date()); // 현재 날짜로 설정

		paymentService.insertPaymentInfo(paymentDTO);

		if (cart_id != null) // 장바구니에서 결제한 경우 실행됨
			cartservice.deleteCart(cart_id);

		model.addAttribute("buyerName", buyer_name);
		model.addAttribute("productName", product_name);
		model.addAttribute("productPrice", product_price);
		model.addAttribute("buyerTel", buyer_tel);

		return "pay_end"; // 성공 페이지로 리다이렉트
	}

	@GetMapping("/success-page")
	public String handlePaymentSuccessGet(Model model) {

		model.addAttribute("message", "잘못된 접근입니다. 결제 후에 다시 시도해주세요.");
		return "error"; // error.html 페이지로 리다이렉트
	}

	@PostMapping("/pay_history")
	public String paymentHistoryPage(@RequestParam(required = false) String orderNumber,
			@RequestParam(required = false) String phoneNumber, Model model) {

		// 결제 내역 조회
		List<PaymentDTO> payments = paymentService.getPaymentHistory(orderNumber, phoneNumber);

		// 조회된 결제 내역을 모델에 추가
		model.addAttribute("payments", payments);

		// 결제 내역 페이지로 이동
		return "payHistory"; // payHistory.html로 이동
	}

	@RequestMapping("/babo")
	public ResponseEntity<?> payfirst(HttpServletRequest request, Model model) {

		String jwtToken = null;
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					jwtToken = cookie.getValue(); // 쿠키에서 jwtToken 값을 가져옵니다.
					break;
				}
			}
		}
		return ResponseEntity.ok(jwtToken);

	}

	@ResponseBody
	@GetMapping("/api/getPhoneNumber")
	public ResponseEntity<String> getPhoneNumber(HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.badRequest().body("로그인이 필요합니다.");
		}

		String phoneNumber = paymentService.getTel(userId); // 전화번호 조회
		return ResponseEntity.ok(phoneNumber); // 전화번호 반환
	}

}