// 아이디 중복 확인 요청
function checkId() {
	const id = document.getElementById('id').value;

	// 아이디 유효성 검사 (영문자 + 숫자, 최소 8자, 최대 20자)
	const idPattern = /^[a-zA-Z0-9]{8,20}$/; // 최소 8자, 최대 20자
	if (!idPattern.test(id)) {
		alert('아이디는 영문자와 숫자만 사용해야 하며, 최소 8자, 최대 20자여야 합니다.');
		return;
	}

	// 영문자와 숫자가 모두 포함되어 있는지 확인
	const hasLetter = /[a-zA-Z]/.test(id);
	const hasNumber = /\d/.test(id);
	if (!hasLetter || !hasNumber) {
		alert('아이디는 반드시 영문자와 숫자가 모두 포함되어야 합니다.');
		return;
	}

	// 서버로 중복 확인 요청
	fetch('/kaja/checkid', {
		method: 'post',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: 'id=' + encodeURIComponent(id)
	})
		.then(response => response.json())  // 서버에서 JSON 형식으로 반환받기
		.then(data => {
			const resultElement = document.getElementById('idCheckResult');
			if (data === true) {
				resultElement.style.color = 'red';
				resultElement.textContent = '이미 사용 중인 아이디입니다.';
			} else {
				resultElement.style.color = 'green';
				resultElement.textContent = '사용 가능한 아이디입니다.';
			}
		})
		.catch(error => {
			console.error('아이디 중복 확인 요청 실패:', error);
		});
}

// 회원가입 폼 제출 시 추가적인 유효성 검사
document.getElementById('join_form').addEventListener('submit', function(event) {
	const id = document.getElementById('id').value;
	const name = document.getElementById('name').value;
	const email = document.getElementById('email').value;
	const password = document.getElementById('password2').value;
	const tel = document.getElementById('tel').value;
	const birth = document.getElementById('birth').value;

	// 아이디 유효성 검사
	const idPattern = /^[a-zA-Z0-9]{8,20}$/;
	if (!idPattern.test(id)) {
		alert('아이디는 영문자와 숫자만 사용해야 하며, 최소 8자, 최대 20자여야 합니다.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

	// 아이디는 반드시 영문자와 숫자가 포함되어야 함
	const hasLetter = /[a-zA-Z]/.test(id);
	const hasNumber = /\d/.test(id);
	if (!hasLetter || !hasNumber) {
		alert('아이디는 반드시 영문자와 숫자가 모두 포함되어야 합니다.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

	// 이름 유효성 검사 (한글만, 최소 2자, 최대 20자)
	const namePattern = /^[가-힣]{2,20}$/; // 최소 2자, 최대 20자
	if (!namePattern.test(name)) {
		alert('이름은 한글만 사용해야 하며, 최소 2자, 최대 20자여야 합니다.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

	// 이메일 입력 확인
	if (!email) {
		alert('이메일을 입력해주세요.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

	// 비밀번호 유효성 검사
	const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!"#$%&'()*+,-./:;<=>?@[\]₩^_`{|}~]).{8,16}$/;
	if (!passwordPattern.test(password)) {
		alert('비밀번호는 8~16자, 영문 대소문자, 숫자, 그리고 특수문자( !"#$%&\'()*+,-./:;<=>?@[\]₩^_`{|}~ )만 허용됩니다.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

	// 전화번호 형식 확인
	const telPattern = /^010-\d{4}-\d{4}$/;
	if (!telPattern.test(tel)) {
		alert('전화번호는 010-xxxx-xxxx 형식이어야 합니다.');
		event.preventDefault();  // 폼 제출 방지
		return false;
	}

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

	// 모든 유효성 검사를 통과하면 폼 제출
	return true;
});

// 페이지 로드 시 15세 이상, 100세 이하 날짜 설정 (생년월일 입력 필드)
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
