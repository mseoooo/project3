
package com.sboot.kaja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sboot.kaja.dao.MapDAO;
import com.sboot.kaja.vo.MapVO;
import com.sboot.kaja.vo.MapVO2;

@Service
public class MapService {
	@Autowired
	private MapDAO mapservice;
	  
	public List<MapVO> getAllMapxy() {
		return mapservice.getAllMapxy();
	}
	
	public List<MapVO2> getAllMapxy2() {
		return mapservice.getAllMapxy2();
	}


}
