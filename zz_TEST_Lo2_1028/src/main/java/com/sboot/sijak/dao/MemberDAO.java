package com.sboot.sijak.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sboot.sijak.vo.MemberVo;
import com.sboot.sijak.vo.PaymentDTO;

@Mapper
public interface MemberDAO {
	public int memberJoin(String id, String name, String email, String password, String tel, Date birth);

	// 아이디로 사용자 조회 메서드 추가 10/20
	MemberVo findByName(String id);

	// 아이디로 중복확인 메서드 추가
	public int existsByUsername(String id);
	
	List<PaymentDTO> getPaymentHistory(String buyerName);

	public void updateMember(MemberVo memberVo);

	MemberVo getInfo(String id);
	
	public String rolecheck(String id);
	
}
