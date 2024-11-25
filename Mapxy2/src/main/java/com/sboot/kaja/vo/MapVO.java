package com.sboot.kaja.vo;

import lombok.Data;

@Data
// 관광지 DTO
public class MapVO {
	private String place;
	private String sigungu;
	private String address;
	private String overview;
	private String overview40;
	private float longtitude;
	private float altitude;
}
