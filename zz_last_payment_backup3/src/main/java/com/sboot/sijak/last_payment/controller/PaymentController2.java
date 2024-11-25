package com.sboot.sijak.last_payment.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.service.PaymentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController2 {

	@Autowired
	private PaymentService paymentService;

	// 날짜 형식 설정
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/time")
	public String monthlyPurchasePage(@RequestParam(required = false) Date purchaseDate, Model model,
			HttpSession session) {
		
		purchaseDate = new Date(System.currentTimeMillis());
		
		// 월매출 및 일매출 계산
		if (purchaseDate != null) {
			int monthlySales = paymentService.calculateTotalProductPrice(purchaseDate);
			int todaySales = paymentService.calculateTotalProductPrice2(purchaseDate);

			model.addAttribute("monthlySales", monthlySales);
			model.addAttribute("todaySales", todaySales);
		}

		// 현재 날짜에서 월 계산
		Date currentDate = new Date(System.currentTimeMillis());
		int thisMonth = paymentService.thisMonth(currentDate);
		String todayDate = paymentService.todayDate(currentDate);

		model.addAttribute("thisMonth", thisMonth);
		model.addAttribute("todayDate", todayDate);
		model.addAttribute("purchaseDate", purchaseDate);

		return "shophome2"; // shophome2.html로 이동
	}

	@GetMapping("/recentPayHistory")
	public String getRecentPayHistory(@RequestParam(defaultValue = "1") int page, Model model) {
		int pageSize = 10; // 한 페이지당 보여줄 개수
		int totalRecords = paymentService.getTotalRecords(); // 전체 레코드 수를 가져옴
		int totalPages = (totalRecords > 0) ? (int) Math.ceil((double) totalRecords / pageSize) : 0; // 총 페이지 수 계산

		List<PaymentDTO> recentPayHistory = paymentService.getRecentPayHistory(page, pageSize); // 현재 페이지의 결제 목록

		if (page < 1) {
			page = 1; // 페이지가 1보다 작으면 1로 설정
		} else if (page > totalPages) {
			page = totalPages; // 페이지가 총 페이지 수보다 크면 마지막 페이지로 설정
		}

		model.addAttribute("recentPayHistory", recentPayHistory);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		return "recentPayHistory"; // 뷰 반환
	}

	@PostMapping("/viewuserdata")
	public String viewUserData(@RequestParam(required = false) Date purchaseDate, Model model) {

		model.addAttribute("purchaseDate", purchaseDate);
		return "viewuserdata"; // viewuserdata.html로 이동
	}

	@RequestMapping("/admin")
	public String goAdmin2() {
		return "redirect:/time";
	}
}