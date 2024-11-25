package com.sboot.kaja.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sboot.kaja.vo.MapVO;
import com.sboot.kaja.vo.MapVO2;

import lombok.Data;

@Mapper 
public interface MapDAO {
	// 관광지DB 모두 가져오기
	List<MapVO> getAllMapxy();
	// 식당&카페 DB 모두 가져오기
	List<MapVO2> getAllMapxy2();
}
