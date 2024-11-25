package com.sboot.sijak.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberVo {
	private String id;
	private String name;
	private String email;
	private String password;
	private String tel;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;	
}