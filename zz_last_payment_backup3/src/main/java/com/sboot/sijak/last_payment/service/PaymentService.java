package com.sboot.sijak.last_payment.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.mapper.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;

	public void insertPaymentInfo(PaymentDTO paymentDTO) {
		paymentMapper.insertPaymentInfo(paymentDTO);
	}

	// 결제 내역 조회 (주문번호 또는 전화번호로 조회)
	public List<PaymentDTO> getPaymentHistory(String orderNumber, String phoneNumber) {
		if (orderNumber != null) {
			// 주문번호로 결제 내역 조회
			return paymentMapper.getPaymentHistoryByOrderNumber(orderNumber);
		} else if (phoneNumber != null) {
			// 전화번호로 결제 내역 조회
			return paymentMapper.getPaymentHistoryByPhoneNumber(phoneNumber);
		}
		return null; // 주문번호와 전화번호가 모두 없으면 null 반환
	}

	public List<PaymentDTO> getMonthlySales(Date purchaseDate) {
		return paymentMapper.getMonthlySales(purchaseDate);
	}

	public int getTotalRecords() {
		return paymentMapper.getTotalRecords(); // Mapper에서 호출
	}

	public List<PaymentDTO> getRecentPayHistory(int page, int pageSize) {
		int startRow = (page - 1) * pageSize + 1; // 시작 행
		int endRow = page * pageSize; // 끝 행

		Map<String, Integer> params = new HashMap<>();
		params.put("startRow", startRow);
		params.put("endRow", endRow);

		return paymentMapper.getRecentPayHistory(params); // Mapper에서 호출
	}

	// 오늘 날짜의 판매 데이터를 가져오는 메서드 (가정)
	private List<PaymentDTO> getTodaySales(Date purchaseDate) {
		// 데이터베이스에서 오늘 날짜의 판매 데이터를 가져오는 로직 구현
		// 예시로 빈 리스트 반환
		return paymentMapper.getTodaySales(purchaseDate);
	}

	// 결제 금액의 "월매출" 총합을 계산하는 메소드
	public int calculateTotalProductPrice(Date purchaseDate) {
		List<PaymentDTO> salesList = getMonthlySales(purchaseDate);
		int total = 0; // 총합을 저장할 변수

		for (PaymentDTO payment : salesList) {
			if (payment != null) { // null 체크 추가
				total += payment.getProductPrice(); // productPrice 값을 합산
			}
		}

		return total; // 총합 반환
	}

	// 결제 금액의 "일매출" 총합을 계산하는 메소드
	public int calculateTotalProductPrice2(Date purchaseDate) {

		// 오늘 날짜를 java.sql.Date로 가져오기
		Date todayDate = new Date(System.currentTimeMillis());

		List<PaymentDTO> salesList2 = getTodaySales(todayDate);
		int total2 = 0; // 총합을 저장할 변수

		for (PaymentDTO payment : salesList2) {
			if (payment != null) { // null 체크 추가
				total2 += payment.getProductPrice(); // productPrice 값을 합산
			}
		}

		return total2; // 총합 반환
	}

	public int thisMonth(Date sysDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sysDate);
		return calendar.get(Calendar.MONTH) + 1; // 1부터 시작
	}

	public String todayDate(Date sysDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sysDate);

		int year = calendar.get(Calendar.YEAR); // 년
		int month = calendar.get(Calendar.MONTH) + 1; // 월 (1부터 시작)
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return String.format("%04d년-%02d월-%02d일", year, month, day);
	}

	public int updateMemberByAdmin(PaymentDTO paymentDTO) {

		return paymentMapper.updateMemberByAdmin(paymentDTO);

	}

	public int deleteMemberByAdmin(String id) {
		// 회원 삭제를 위한 매퍼 메서드 호출
		return paymentMapper.deleteMemberByAdmin(id);
	}

	public String getTel(String id) {

		return paymentMapper.getBuyerTel(id);
	}
}