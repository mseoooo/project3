package com.sboot.sijak.member.Service;

import java.util.List;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.sijak.last_payment.dto.PaymentDTO;
import com.sboot.sijak.last_payment.mapper.PaymentMapper;

@Service
public interface MemberService {

	List<PaymentDTO> getAllMembers(); // 모든 회원 정보를 가져오는 메서드

	int getTotalRecords2();

	List<PaymentDTO> getMemberInfo(int page, int pageSize);

}
