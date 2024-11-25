package com.sboot.sijak.member.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.mapper.PaymentMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private PaymentMapper paymentMapper; // MyBatis Mapper 주입

	@Override
	public List<PaymentDTO> getAllMembers() {
		return paymentMapper.getMemberInfo(); // Mapper를 통해 회원 정보 가져오기
	}

	@Override
	public int getTotalRecords2() {
		return paymentMapper.getTotalRecords2(); // Mapper에서 호출
	}

	@Override
	public List<PaymentDTO> getMemberInfo(int page, int pageSize) {
		int startRow = (page - 1) * pageSize + 1; // 시작 행
		int endRow = page * pageSize; // 끝 행

		Map<String, Integer> params = new HashMap<>();
		params.put("startRow", startRow);
		params.put("endRow", endRow);

		return paymentMapper.getMemberInfo(params); // Mapper에서 호출
	}
}
