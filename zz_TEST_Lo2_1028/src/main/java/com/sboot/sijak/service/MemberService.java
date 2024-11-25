package com.sboot.sijak.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.sijak.dao.MemberDAO;
import com.sboot.sijak.vo.MemberVo;
import com.sboot.sijak.vo.PaymentDTO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;

	public int joinMember(String id, String name, String email, String password, String tel, Date birth) {
		return memberDAO.memberJoin(id, name, email, password, tel, birth);

	}

	// 이름으로 사용자 조회 10/20 추가 SS
	public MemberVo findByName(String id) {
		return memberDAO.findByName(id); // Mapper를 사용해 사용자 조회
	}

	// 아이디로 중복확인
	public boolean checkUsernameExists(String id) {
		return memberDAO.existsByUsername(id) > 0; // 결과가 0보다 크면 true, 아니면 false
	}

	public List<PaymentDTO> getPaymentHistory(String buyerName) {
		return memberDAO.getPaymentHistory(buyerName);
	}

	public void updateMemberInfo(MemberVo memberVo) {

		memberDAO.updateMember(memberVo);
	}

	public MemberVo getInfo(String id) {
		return memberDAO.getInfo(id);
	}

	public String rolecheck(String id) {
		String role;
		role = memberDAO.rolecheck(id);
		return role;
	}
}