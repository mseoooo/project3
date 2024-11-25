package com.sboot.sijak.sijak.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sboot.sijak.dao.MemberDAO;
import com.sboot.sijak.vo.MemberVo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberDAO memberDAO;

	public UserDetailsServiceImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		// 사용자 정보를 데이터베이스에서 가져오기
		MemberVo username = memberDAO.findByName(id); // 비밀번호는 이 시점에서는 사용하지 않음

		if (username != null) {
			// 데이터베이스에서 가져온 암호화된 비밀번호를 사용
			String encodedPassword = username.getPassword(); // 데이터베이스에서 가져온 암호화된 비밀번호
			return User.builder().username(username.getName()).password(encodedPassword) // 암호화된 비밀번호 사용
					.roles("USER").build();
		} else {
			throw new UsernameNotFoundException("User not found");
		}
	}
}
