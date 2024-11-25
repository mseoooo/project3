package com.sboot.sijak.last_payment.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sboot.sijak.last_payment.dto.PaymentDTO;

@Mapper
public interface PaymentMapper {
	void insertPaymentInfo(PaymentDTO paymentdto);

	List<PaymentDTO> getPaymentHistory(String buyerName);

	List<PaymentDTO> getMonthlySales(Date purchaseDate);

	List<PaymentDTO> getTodaySales(Date purchaseDate);

	List<PaymentDTO> getRecentPayHistory(Map<String, Integer> params);

	int getTotalRecords();

	List<PaymentDTO> getThreeMSales();

	List<PaymentDTO> getRegionSales();

	List<PaymentDTO> getMemberInfo();

	int getTotalRecords2();

	List<PaymentDTO> getMemberInfo(Map<String, Integer> params);

	int updateMemberByAdmin(PaymentDTO paymentDTO);

	PaymentDTO getOneInfo(String id); // 수정된 정보 조회

	// 회원 삭제
	int deleteMemberByAdmin(String id);

	List<PaymentDTO> getPaymentHistoryByOrderNumber(String orderNumber);

	List<PaymentDTO> getPaymentHistoryByPhoneNumber(String phoneNumber);

	public String getBuyerTel(String id);
}
