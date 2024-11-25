// member-info.js

document.addEventListener("DOMContentLoaded", function() {
	// 모든 "member-info" 클래스를 가진 링크에 대해 클릭 이벤트 처리
	const memberLinks = document.querySelectorAll('.member-info');

	memberLinks.forEach(link => {
		link.addEventListener('click', function() {
			// 클릭된 링크에서 데이터 속성 가져오기
			const id = this.getAttribute('data-id');
			const userName = this.getAttribute('data-userName');
			const email = this.getAttribute('data-email');
			const tel = this.getAttribute('data-tel');
			const birth = this.getAttribute('data-birth');

			// showMemberInfo 함수 호출 (모달에 정보 표시)
			showMemberInfo(id, userName, email, tel, birth);
		});
	});
	
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

// 모달 열기
function showMemberInfo(id, userName, email, tel, birth) {
	// 기존 값을 모달에 표시
	document.getElementById('modalId').innerText = id;
	document.getElementById('modalUserName').value = userName;
	document.getElementById('modalEmail').value = email;
	document.getElementById('modalTel').value = tel;
	document.getElementById('modalBirth').value = birth;

	// 수정 버튼과 저장 버튼 초기 상태 설정
	document.getElementById('editButton').style.display = 'inline-block';
	document.getElementById('saveButton').style.display = 'none';

	// 모달 표시
	document.getElementById('memberInfoModal').style.display = 'block';
}

// 모달 닫기
function closeModal() {
	document.getElementById('memberInfoModal').style.display = 'none';
}

// 수정 버튼 클릭
document.getElementById('editButton').addEventListener('click', function() {
	// 입력 필드를 수정 가능하게 만듬
	document.getElementById('modalUserName').disabled = false;
	document.getElementById('modalEmail').disabled = false;
	document.getElementById('modalTel').disabled = false;
	document.getElementById('modalBirth').disabled = false;

	// 수정 버튼 숨기고, 저장 버튼 표시
	document.getElementById('editButton').style.display = 'none';
	document.getElementById('saveButton').style.display = 'inline-block';
});


// 저장 버튼 클릭 시
document.getElementById('saveButton').addEventListener('click', function() {
	const id = document.getElementById('modalId').innerText;
	const userName = document.getElementById('modalUserName').value;
	const tel = document.getElementById('modalTel').value;
	const birth = document.getElementById('modalBirth').value;

	// 이메일 유효성 검사 (정규 표현식 사용)
	const emailInput = document.getElementById('modalEmail');
	const emailValue = emailInput.value;
	const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!emailPattern.test(emailValue)) {
		alert('유효한 이메일 주소를 입력해주세요.');
		return false;  // 저장되지 않음
	}

	// 전화번호 유효성 검사 (정규 표현식 사용)
	const phonePattern = /^010-\d{4}-\d{4}$/;
	if (tel && !phonePattern.test(tel)) {
		alert('전화번호는 010-xxxx-xxxx 형식으로 입력해주세요.');
		return false;  // 저장되지 않음
	}

	// 이름 유효성 검사 (한글만)
	const namePattern = /^[가-힣]+$/;
	if (userName && !namePattern.test(userName)) {
		alert('이름은 한글만 입력 가능합니다.');
		return false;  // 저장되지 않음
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


	// 모든 검사를 통과하면 서버로 데이터 전송
	fetch('/updateMember', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',  // 서버에 JSON 형태로 데이터 전달
		},
		body: JSON.stringify({
			id: id,
			name: userName,
			email: emailValue,
			tel: tel,
			birth: birth
		})
	})
		.then(response => response.json())  // 서버에서 JSON 응답을 받을 때
		.then(data => {
			if (data.success) {
				alert(`회원 정보 수정됨:
              ID: ${id}
              이름: ${userName}
              이메일: ${emailValue}   
              전화번호: ${tel}
              생년월일: ${birth}`);

				location.reload(); // 페이지 새로 고침 (수정된 정보가 화면에 반영되도록)
			} else {
				alert('회원 정보 수정에 실패했습니다.');
			}
		})
		.catch(error => {
			console.error('에러 발생:', error);
			alert('서버와의 통신 중 오류가 발생했습니다.');
		});

	// 모달 닫기
	closeModal();
});

// 삭제 버튼 클릭 시
document.getElementById('deleteButton').addEventListener('click', function() {
	const id = document.getElementById('modalId').innerText;

	if (confirm(`정말로 ID: ${id} 회원 정보를 삭제하시겠습니까?`)) {
		fetch('/deleteMember', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ id: id })  // 회원 ID만 서버로 보냄
		})
			.then(response => response.json())  // 서버에서 JSON 응답을 받을 때
			.then(data => {
				if (data.success) {
					alert('회원 정보가 삭제되었습니다.');
					// 삭제 후 모달 닫고, 삭제된 항목을 화면에서 제거
					closeModal();
					location.reload(); // 페이지 새로 고침 (삭제된 정보가 화면에 반영되도록)
				} else {
					alert('회원 정보 삭제에 실패했습니다.');
				}
			})
			.catch(error => {
				console.error('에러 발생:', error);
				alert('서버와의 통신 중 오류가 발생했습니다.');
			});
	}
});

