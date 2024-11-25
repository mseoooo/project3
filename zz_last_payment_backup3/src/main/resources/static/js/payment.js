var IMP = window.IMP;
IMP.init("imp21157764");

async function validatePayment() {
	// JWT 유효성 검사 요청
	try {
		const response = await fetch('/babo', {
			method: 'GET', // 기본적으로 GET 요청
			headers: {
				'Content-Type': 'application/json'
			}
		});
		const data = await response.text(); // 응답 JSON 처리
		// 응답 데이터를 변수에 저장
		let responseData = data;

		// JWT 토큰 유효성 검사
		return await validateJwtToken(responseData);
	} catch (error) {
		console.error("Error:", error); // 오류 발생 시 처리
		alert("토큰 유효성 검사 중 오류가 발생했습니다.");
		return false;  // 오류가 발생하면 결제 진행 안함
	}
}

async function validateJwtToken(responseData) {
	try {
		const response = await fetch('http://localhost:8030/jwt/validate', {
			method: 'GET', // 기본적으로 GET 요청
			headers: {
				'Content-Type': 'application/json',
				'Authorization': 'Bearer ' + responseData // JWT 토큰을 헤더에 추가
			}
		});

		const data = await response.json(); // 서버에서 JSON으로 응답
		if (data) {
			console.log("Token is valid, proceeding with payment.");
			return true;  // 토큰이 유효하면 결제 진행
		} else {
			alert("토큰이 유효하지 않거나 만료되었습니다. 로그인 후 다시 시도해주세요.");
			window.location.href = "/login";  // 로그인 페이지로 리디렉션
			return false;  // 유효하지 않으면 결제 진행 안함
		}
	} catch (error) {
		console.error("Error validating token:", error);
		alert("토큰 유효성 검사 중 오류가 발생했습니다.");
		window.location.href = "/login";  // 로그인 페이지로 리디렉션
		return false;
	}
}

async function requestPay(buttonElement) {
	// validatePayment에서 반환된 값을 기다리기 위해 `await` 사용
	const isValid = await validatePayment();
	if (!isValid) {
		return false;  // 유효성 검사 실패 시 결제 진행 안함
	}
	
	const userId = sessionStorage.getItem('userId');
	const productName = buttonElement.dataset.productName;  // 상품 이름
	const productPrice = buttonElement.dataset.productPrice;  // 상품 가격
	const cart_ID = buttonElement.dataset.cartId;  // 장바구니 ID (있을 경우)

	// 전화번호 가져오기
	let phoneNumber;
	try {
		const response = await fetch('/api/getPhoneNumber');
		if (response.ok) {
			phoneNumber = await response.text();
		} else {
			alert("전화번호를 가져오는데 실패했습니다. 로그인 상태를 확인해주세요.");
			return;
		}
	} catch (error) {
		console.error("Error fetching phone number:", error);
		alert("전화번호 조회 중 문제가 발생했습니다.");
		return;
	}

	// 결제 요청
	IMP.request_pay({
		pg: 'kakaopay.TC0ONETIME',
		pay_method: 'card',
		merchant_uid: "order_" + new Date().getTime(),
		name: productName,
		amount: productPrice,
		buyer_name: userId,
		buyer_tel: phoneNumber
	}, function(rsp) { // callback
		if (rsp.success) {
			// POST 방식으로 성공 페이지로 리다이렉트
			const form = document.createElement("form");
			form.method = "POST";
			form.action = "/success-page";

			const input1 = document.createElement("input");
			input1.type = "hidden";
			input1.name = "merchant_uid";
			input1.value = rsp.merchant_uid;
			form.appendChild(input1);

			const input2 = document.createElement("input");
			input2.type = "hidden";
			input2.name = "buyer_name";
			input2.value = rsp.buyer_name;
			form.appendChild(input2);

			const input3 = document.createElement("input");
			input3.type = "hidden";
			input3.name = "buyer_tel";
			input3.value = rsp.buyer_tel;
			form.appendChild(input3);

			const input4 = document.createElement("input");
			input4.type = "hidden";
			input4.name = "product_name";
			input4.value = productName;
			form.appendChild(input4);

			const input5 = document.createElement("input");
			input5.type = "hidden";
			input5.name = "product_price";
			input5.value = productPrice;
			form.appendChild(input5);

			if (cart_ID != null) {
				const input6 = document.createElement("input");
				input6.type = "hidden";
				input6.name = "cart_id";
				input6.value = cart_ID;
				form.appendChild(input6);
			}

			document.body.appendChild(form);
			form.submit(); // 폼 제출
		} else {
			alert("결제에 실패했습니다. 에러: " + rsp.error_msg + " 다시 시도해주세요");
			window.location.href = "/shop";
		}
	});
}

