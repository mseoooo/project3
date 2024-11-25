package com.sboot.sijak;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
///import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sboot.sijak.jwtutil.JwtUtil;
import com.sboot.sijak.service.MemberService;

@CrossOrigin(origins = "http://localhost:8810, http://localhost:8080, http://localhost:8030,http://localhost:8088,http://localhost:8761", allowCredentials = "true")
@Controller
public class KajaController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MemberService memberService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping("/shop")
	public String login22() {
		return "redirect:http://localhost:8088/shop";
	}

	@RequestMapping("/trip")
	public String goTrip() {
		return "redirect:http://localhost:8080/trip";
	}

	// ----------------------------------------
	@RequestMapping(value = { "/home", "/", "/bb", "/cc" })
	public String kaja(HttpServletRequest request) {
		String jwtToken = null;
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					jwtToken = cookie.getValue(); // 쿠키에서 jwtToken 값을 가져옵니다.
					break;
				}
			}
		}

		String userName = jwtUtil.getUserNameFromToken(jwtToken);
		String userId = jwtUtil.getUserIdFromToken(jwtToken);
		if (userId == null) {

			return "thymeleaf/index";
		}

		HttpSession session = request.getSession();
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("userName", userName);

		return "thymeleaf/index_home"; // 인증된 사용자만 접근 가능
	}

	@RequestMapping("/kaja/login")
	public String login() {
		return "kaja/join";
	}

	@RequestMapping("/kaja/join")
	public String hhhh() {

		return "kaja/join";
	}

	@RequestMapping("/kaja/loginwelcome")
	public String welcomePage() {
		return "kaja/loginwelcome";
	}

	@RequestMapping("/loginwelcome")
	public String welcomePage2() {
		return "kaja/loginwelcome";
	}

	@RequestMapping("/kaja/mainhome")
	public String mainhome() {
		return "kaja/mainhome";
	}

	@RestController
	@RequestMapping("/kaja/memberJoin")
	public class MemberJoinController {

		@Autowired
		private MemberService memberService;
		@Autowired
		BCryptPasswordEncoder passwordEncoder;

		@PostMapping
		public void joinMember(@RequestParam String id, @RequestParam String name, @RequestParam String email,
				@RequestParam String password2, @RequestParam String tel, @RequestParam Date birth,
				HttpServletResponse response) throws IOException {

			// 비밀번호 암호화
			String password = passwordEncoder.encode(password2);

			// 회원가입 처리
			int result = memberService.joinMember(id, name, email, password, tel, birth);

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.println("<script>alert('회원 가입이 완료되었습니다.'); location.href='/';</script>");
			} else {
				out.println("<script>alert('회원 가입에 실패했습니다. 다시 시도해 주세요.'); location.href='/';</script>");
			}
		}

		// 오류 메시지를 반환하는 메서드
		private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('" + errorMessage + "'); location.href='/';</script>");
		}
	}

	@RequestMapping("/kaja/successPage")
	public String go() {

		return "kaja/successPage";
	}

	@RequestMapping("/kaja/errorPage")
	public String go2() {

		return "kaja/errorPage";
	}

	// 로그인 처리
	@RestController
	@RequestMapping("/loginHaja2")
	public class MemberLoginController {

		@Autowired
		private MemberService memberService;

		@Autowired
		BCryptPasswordEncoder passwordEncoder;

		@Autowired
		private RestTemplate restTemplate;

		// 로그인 처리 메서드
		@PostMapping
		public ResponseEntity<String> loginMember(@RequestParam String id, @RequestParam String password2,
				HttpServletRequest request, HttpServletResponse response) {
			// JWT 서버의 로그인 엔드포인트
			String jwtLoginUrl = "http://localhost:8030/jwt/login";

			// 로그인에 필요한 데이터 설정 (id와 password)
			Map<String, String> loginRequest = new HashMap<>();
			loginRequest.put("id", id);
			loginRequest.put("password", password2);

			// JWT 서버에 POST 요청 보내기

			try {
				// REST 요청을 보내기 위한 HttpEntity 생성
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				HttpEntity<Map<String, String>> entity = new HttpEntity<>(loginRequest, headers);

				// JWT 서버에서 토큰을 받아오기
				ResponseEntity<Map> responseEntity = restTemplate.exchange(jwtLoginUrl, HttpMethod.POST, entity,
						Map.class);

				// JWT 토큰을 받아서 처리
				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					String token = (String) responseEntity.getBody().get("token");
					String refreshToken = (String) responseEntity.getBody().get("refreshToken");

					Cookie cookie = new Cookie("jwtToken", token);
					cookie.setHttpOnly(true); // 클라이언트에서 자바스크립트로 쿠키에 접근할 수 없도록 설정
					cookie.setSecure(false); // HTTPS 연결에서만 쿠키를 전송하도록 설정
					cookie.setPath("/"); // 모든 경로에서 쿠키를 사용하도록 설정
					cookie.setMaxAge(60 * 60 * 24); // 쿠키 만료 시간 설정 (예: 1일)
					cookie.setDomain("localhost"); // Cross-domain 쿠키 허용 (필요시 도메인 설정)
					response.addCookie(cookie);

					response.setHeader("Set-Cookie", "jwtToken=" + token
							+ "; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=86400; Domain=localhost"); // 다른
																												// 도메인에서도
																												// 쿠키를
																												// 전송하도록
																												// 설정
					// JwtConfig를 통해 설정 값을 불러옴
					Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
					// refreshTokenCookie.setHttpOnly(true);
					refreshTokenCookie.setHttpOnly(true); // 클라이언트에서 자바스크립트로 쿠키에 접근할 수 없도록 설정
					refreshTokenCookie.setSecure(false); // HTTPS 연결에서만 쿠키를 전송하도록 설정
					refreshTokenCookie.setPath("/"); // 모든 경로에서 쿠키를 사용하도록 설정
					refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
					refreshTokenCookie.setDomain("localhost"); // Cross-domain 쿠키 허용 (필요시 도메인 설정)

					response.addCookie(refreshTokenCookie);
					response.setHeader("Set-Cookie2", "refreshToken=" + refreshToken + "; SameSite=None;");

					// 쿠키를 응답에 추가

					HttpSession session = request.getSession();
					session.setAttribute("jwtToken", token);
					session.setAttribute("userId", id);
					String welcomeMessage = "<script>" + "alert('" + id + "님, 환영합니다!');" + "setTimeout(function() {"
							+ "   var form = document.createElement('form');" + "   form.method = 'post';"
							+ "   form.action = '/home';" + "   var input = document.createElement('input');"
							+ "   input.type = 'hidden';" + "   input.name = 'id';" + "   input.value = '" + id + "';"
							+ "   form.appendChild(input);" + "   document.body.appendChild(form);"
							+ "   form.submit();" + "}, 10);</script>";

					// 로그인 성공 후 클라이언트로 응답
					return ResponseEntity.ok(welcomeMessage);
				} else {
					return ResponseEntity.badRequest().body("Login failed");
				}
			} catch (Exception e) {
				e.printStackTrace();
				String errorMessage = "<script>" + "alert('아이디 또는 비밀번호가 틀렸습니다. 다시 확인해주세요.');"
						+ "setTimeout(function() { window.location.href = '/'; }, 30);" + "</script>";
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
			}
		}
	}
// 10/20 추가      

	// 아이디 중복 확인
	@PostMapping("/kaja/checkid")
	public ResponseEntity<?> payfirst(@RequestParam String id, Model model) {
		System.out.println("몰라몰라" + id);
		boolean exists = memberService.checkUsernameExists(id); // 메서드 호출, 아이디존재여부 검사, exists 변수에 결과 저장
		model.addAttribute("exists", exists); // jsp에서 조건문 사용해 아이디 존재 여부 판단
		model.addAttribute("username", id); // 입력된 사용자명 추가
		System.out.println(exists);

		return ResponseEntity.ok(exists);

	}

	@PostMapping("/kaja/loginwelcome2")
	public String view(HttpServletRequest req1, HttpServletResponse response, HttpSession session, Model model1) {

		String jwtToken = (String) session.getAttribute("jwtToken");

		return "redirect:/";

	}

	// 로그아웃
	@PostMapping("/logout")
	public String logout(HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes redirectAttributes, HttpSession session) {
		// 세션을 무효화하여 로그아웃 처리
		if (session != null) {
			session.invalidate();
		}

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName()) || "refreshToken".equals(cookie.getName())) {
					// 쿠키 이름이 jwtToken 또는 refreshToken인 경우 처리
					Cookie deleteCookie = new Cookie(cookie.getName(), null); // 쿠키 이름과 null 값을 설정
					System.out.println("바보같은 이민혁: " + deleteCookie);

					deleteCookie.setHttpOnly(true); // HttpOnly 속성 설정
					deleteCookie.setPath("/"); // 쿠키의 유효 범위를 애플리케이션 전체로 설정
					deleteCookie.setDomain("localhost"); // 도메인 설정
					deleteCookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 삭제

					response.addCookie(deleteCookie); // 쿠키를 응답에 추가하여 클라이언트에서 삭제되도록 처리
				}
			}

		}
		redirectAttributes.addFlashAttribute("message", "로그아웃 되었습니다.");

		return "redirect:/"; // 로그인 페이지로 리디렉션
	};

	@RequestMapping("/adminPage")
	public String rolecheck(HttpServletResponse response, HttpSession session, HttpServletRequest request,
			Model model) {

		String id = (String) session.getAttribute("userId");
		String role = memberService.rolecheck(id);
		if (role.equals("admin")) {
			return "redirect:http://localhost:8088/admin"; // 결과를 보여줄 JSP 페이지
		} else {
			return "kaja/errorPage";
		}

	}
}
