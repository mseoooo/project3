package com.sboot.sijak;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sboot.sijak.service.MemberService;
import com.sboot.sijak.vo.MemberVo;
import com.sboot.sijak.vo.PaymentDTO;


@Controller
public class UpdateMemberController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService memberService;

	@PostMapping("/member/update")
	public String updateMember(@ModelAttribute MemberVo memberVo, Model model) throws IOException {
		if (memberVo.getPassword() == null || memberVo.getPassword().isEmpty()) {
			memberVo.setPassword(null);
		} else {
			String password = passwordEncoder.encode(memberVo.getPassword());
			memberVo.setPassword(password);
		}

		if (memberVo.getName() == null || memberVo.getName().isEmpty()) {
			memberVo.setName(null);
		}
		if (memberVo.getBirth() == null) {
			memberVo.setBirth(null);
		}
		if (memberVo.getEmail() == null || memberVo.getEmail().isEmpty()) {
			memberVo.setEmail(null);
		}
		if (memberVo.getTel() == null || memberVo.getTel().isEmpty()) {
			memberVo.setTel(null);
		}

		memberService.updateMemberInfo(memberVo);
		MemberVo loginInfo = memberService.getInfo(memberVo.getId());
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("payments", memberService.getPaymentHistory(memberVo.getId()));

		return "thymeleaf/mypage";
	}

	@RequestMapping("/mypage")
	public String processMypage(HttpSession session, Model model) {
	
		String username = (String) session.getAttribute("userId");
		
		List<PaymentDTO> payments = memberService.getPaymentHistory(username);
		MemberVo loginInfo = memberService.getInfo(username);
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("payments", payments);
		return "thymeleaf/mypage"; // 결과를 출력할 때 사용할 템플릿
	}
}
