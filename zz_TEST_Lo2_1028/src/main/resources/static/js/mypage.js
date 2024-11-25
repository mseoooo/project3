// 섹션 전환 함수
function showSection(section) {
	const sections = document.querySelectorAll('.section');
	sections.forEach(s => s.classList.remove('active')); // 모든 섹션 숨기기
	document.getElementById(section).classList.add('active'); // 선택한 섹션만 보이기
}

function goHome() {
	window.location.href = "/";
}

document.addEventListener("DOMContentLoaded", function() {
	const nameInput = document.getElementById("name");
	const emailInput = document.getElementById("email");
	const telInput = document.getElementById("tel");

	// 이름: 한글만 입력 가능하도록 검증
	nameInput.addEventListener("input", function() {
		const koreanPattern = /^[가-힣]+$/;
		if (!koreanPattern.test(nameInput.value)) {
			nameInput.setCustomValidity("이름은 한글만 입력할 수 있습니다.");
		} else {
			nameInput.setCustomValidity("");
		}
	});

	// 이메일: 이메일 형식 검증
	emailInput.addEventListener("input", function() {
		const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		if (!emailPattern.test(emailInput.value)) {
			emailInput.setCustomValidity("유효한 이메일 주소를 입력하세요.");
		} else {
			emailInput.setCustomValidity("");
		}
	});

	// 전화번호: 한국 전화번호 형식 검증
	telInput.addEventListener("input", function() {
		const telPattern = /^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;
		if (!telPattern.test(telInput.value)) {
			telInput.setCustomValidity("유효한 전화번호 형식 (예: 010-1234-5678)을 입력하세요.");
		} else {
			telInput.setCustomValidity("");
		}
	});

	// 생년월일 확인 (15세 이상, 100세 이하)
	if (!birth) {
		alert('생년월일을 입력해주세요.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

	const birthDate = new Date(birth);
	const today = new Date();

	// 현재 날짜 기준으로 15세 이상, 100세 이하인지 확인
	const minAgeDate = new Date(today.getFullYear() - 15, today.getMonth(), today.getDate());
	const maxAgeDate = new Date(today.getFullYear() - 100, today.getMonth(), today.getDate());

	// 15세 미만 또는 100세 이상인 경우
	if (birthDate > minAgeDate || birthDate < maxAgeDate) {
		alert('생년월일은 15세 이상 100세 이하이어야 합니다.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}
});

window.addEventListener('DOMContentLoaded', function() {
	const today = new Date();
	const minDate = new Date(today.getFullYear() - 15, today.getMonth(), today.getDate());
	const maxDate = new Date(today.getFullYear() - 100, today.getMonth(), today.getDate());

	const minYear = minDate.getFullYear();
	const minMonth = (minDate.getMonth() + 1).toString().padStart(2, '0');
	const minDay = minDate.getDate().toString().padStart(2, '0');
	const maxYear = maxDate.getFullYear();
	const maxMonth = (maxDate.getMonth() + 1).toString().padStart(2, '0');
	const maxDay = maxDate.getDate().toString().padStart(2, '0');

	const birthInput = document.querySelector('input[name="birth"]');
	birthInput.setAttribute('max', `${minYear}-${minMonth}-${minDay}`);
	birthInput.setAttribute('min', `${maxYear}-${maxMonth}-${maxDay}`);
});


