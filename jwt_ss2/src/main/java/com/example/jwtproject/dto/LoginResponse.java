package com.example.jwtproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String refreshToken;
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	public String getrefreshToken() {
		return refreshToken;
	}

	public void setrefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	  public LoginResponse(String token,String refreshToken) {
	        this.token = token;
	        this.refreshToken=refreshToken;
	    }
	
	
	
    public LoginResponse(String token) {
        this.token = token;
    }

}