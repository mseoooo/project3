package com.example.jwtproject.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

	private final PasswordEncoder passwordEncoder;

	public void encryptPassword() {
		String rawPassword = "password123";
		String encryptedPassword = passwordEncoder.encode(rawPassword);
	}

	public PasswordUtil(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
