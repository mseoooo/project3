package com.sboot.kaja;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sboot.kaja.service.MapService;
import com.sboot.kaja.vo.MapVO;
import com.sboot.kaja.vo.MapVO2;
import com.sboot.kaja.jwtutil.JwtUtil;

@CrossOrigin(origins = "http://localhost:8810, http://localhost:8080, http://localhost:8030,http://localhost:8088", allowCredentials = "true")
@Controller
public class kajaController {

    @Autowired
    private MapService mapservice;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private HttpSession httpSession;
	
       @RequestMapping("/")
    public String goMain() {
    	return "redirect:http://localhost:8810/";
    }
    
    @RequestMapping("/shop")
    public String goShop() {
    	return "redirect:http://localhost:8088/shop";
    }

    @RequestMapping("/trip")
    public String basic_info(HttpServletRequest request) {
    	
    	String jwtToken = null;
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					jwtToken = cookie.getValue(); // 쿠키에서 jwtToken 값을 가져옵니다.
					System.out.println("jwtToken: " + jwtToken);
					break;
				}
			}
		}

		// JWT 토큰에서 사용자 ID를 추출
		String userId = jwtUtil.getUserIdFromToken(jwtToken);
		String userName = jwtUtil.getUserNameFromToken(jwtToken);
		// ID가 유효하지 않다면 로그인 페이지로 리디렉션
		if (userId == null) {
			return "redirect:/";
		}

		// 세션에 사용자 ID를 저장
		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
		session.setAttribute("userName", userName);
		// shop 페이지로 이동 // 실제로는 shop 페이지 템플릿을 반환
        return "thymeleaf/basic_info";
    }
    
    @RequestMapping("/mapxy")
    public String mapxy(HttpServletRequest req, Model model) {
    	// 직전 페이지(basic_info.html) 사용자 입력 정보 부르기
        // 출발지 사용자 입력 정보 받기
        String departure = req.getParameter("departure");
        
        // 일정(몇박 며칠) 입력 정보 받기
        String plan = req.getParameter("plan");

        // 위도 및 경도 정보 받기
        String altitudeParam = req.getParameter("altitude");
        String longtitudeParam = req.getParameter("longtitude");

        // 위도 및 경도 정보를 Float 타입으로 변환
        float altitude = Float.parseFloat(altitudeParam);
        float longtitude = Float.parseFloat(longtitudeParam);

        // 출발지 및 출발 시간 정보를 모델에 추가
        model.addAttribute("departure", departure);
        model.addAttribute("plan",plan);
        model.addAttribute("altitude", altitude);
        model.addAttribute("longtitude", longtitude);        
        
        // DB 연동
        // [관광지]
        // 1. 장소명 전체 데이터 가져오기
        List<MapVO> nearestTouristSpots = mapservice.getAllMapxy();
        List<String> placeNames = nearestTouristSpots.stream()
                .map(MapVO::getPlace)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());

        model.addAttribute("placeNames", placeNames);

        // 2. 설명 전체 데이터 가져오기
        List<String> overviews = nearestTouristSpots.stream()
                .map(MapVO::getOverview40)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());

 
        model.addAttribute("overviews", overviews);
        
        // 3. 위도, 경도 전체 데이터 가져오기
        List<Float> altitudes = nearestTouristSpots.stream()
                .map(MapVO::getAltitude)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());
        List<Float> longtitudes = nearestTouristSpots.stream()
                .map(MapVO::getLongtitude)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());
       
        model.addAttribute("altitudes", altitudes);
        model.addAttribute("longtitudes", longtitudes);	//
        
     // [식당&카페]
        // 1. 장소명 전체 데이터 가져오기
        List<MapVO2> nearestTouristSpots2 = mapservice.getAllMapxy2();
        List<String> placeNames2 = nearestTouristSpots2.stream()
                .map(MapVO2::getPlace)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());

        model.addAttribute("placeNames2", placeNames2);

        // 2. 설명 전체 데이터 가져오기
        List<String> overviews2 = nearestTouristSpots2.stream()
                .map(MapVO2::getOverview40)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());

 
        model.addAttribute("overviews2", overviews2);
        
        // 3. 위도, 경도 전체 데이터 가져오기
        List<Float> altitudes2 = nearestTouristSpots2.stream()
                .map(MapVO2::getAltitude)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());
        List<Float> longtitudes2 = nearestTouristSpots2.stream()
                .map(MapVO2::getLongtitude)  // 각 MapVO 객체에서 장소명 가져오기
                .collect(Collectors.toList());
       
        model.addAttribute("altitudes2", altitudes2);
        model.addAttribute("longtitudes2", longtitudes2);
        
        
         
        return "thymeleaf/mapxy"; // mapxy.html로 이동
    }


}