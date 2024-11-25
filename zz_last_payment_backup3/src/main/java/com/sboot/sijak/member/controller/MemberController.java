package com.sboot.sijak.member.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.service.PaymentService;
import com.sboot.sijak.member.Service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/member-info")
	public String getMemberInfo(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) Date purchaseDate,
			@SessionAttribute(value = "purchaseDate", required = false) Date sessionPurchaseDate, Model model) {
		int pageSize = 20; // 한 페이지당 보여줄 개수

		// purchaseDate가 null이라면 세션에서 가져오거나 기본값 설정
		if (purchaseDate == null) {
			purchaseDate = sessionPurchaseDate != null ? sessionPurchaseDate : new Date(pageSize); // 기본값 설정
		}

		// 전체 레코드 수를 가져옴
		int totalRecords2 = memberService.getTotalRecords2(); // 전체 레코드 수를 가져옴
		int totalPages = (totalRecords2 > 0) ? (int) Math.ceil((double) totalRecords2 / pageSize) : 0; // 총 페이지 수 계산

		// 세션에 purchaseDate 값 저장
		model.addAttribute("purchaseDate", purchaseDate);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		// 현재 페이지 값이 올바른 범위 내에 있는지 확인
		if (page < 1) {
			page = 1; // 페이지가 1보다 작으면 1로 설정
		} else if (page > totalPages && totalPages > 0) {
			page = totalPages; // 페이지가 총 페이지 수보다 크면 마지막 페이지로 설정
		}

		// 현재 페이지의 결제 목록을 가져옴
		List<PaymentDTO> memberInfo = memberService.getMemberInfo(page, pageSize);

		model.addAttribute("purchaseDate", purchaseDate);
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "member3"; // 뷰 반환
	}

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/updateMember")
	public ResponseEntity<?> updateMember(@RequestBody PaymentDTO paymentDTO) {
		int isUpdated = paymentService.updateMemberByAdmin(paymentDTO);

		if (isUpdated > 0) {

			return ResponseEntity.ok().body("{\"success\": true}");
		} else {
			return ResponseEntity.status(500).body("{\"success\": false}");
		}
	}

	@PostMapping("/deleteMember")
	public ResponseEntity<?> deleteMember(@RequestBody Map<String, String> payload) {
		// 회원 정보를 삭제하는 서비스 호출
		String id = payload.get("id"); // JSON 객체에서 id를 꺼냄

		// 회원 정보를 삭제하는 서비스 호출
		int isDeleted = paymentService.deleteMemberByAdmin(id);

		if (isDeleted > 0) {

			return ResponseEntity.ok().body("{\"success\": true}");
		} else {
			return ResponseEntity.status(500).body("{\"success\": false}");
		}
	}
}
