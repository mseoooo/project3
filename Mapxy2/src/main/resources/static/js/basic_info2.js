let submitBtn;

// HTML 요소들이 로드된 후에 이벤트 리스너 추가
window.onload = function() {
	// submitBtn 초기화
	submitBtn = document.getElementById('submit-btn');

	// 종료일이 변경될 때마다 자동으로 NightsDays 함수 실행
	document.getElementById('end-date').addEventListener('change', NightsDays);

	loadHTML('header', 'header.html');
	loadHTML('footer', 'footer.html');

};

/*날짜 선택 제한 및 일정 선택에 따른 O박 O일 출력*/
function NightsDays() {
	const startDateElem = document.getElementById('start-date');
	const endDateElem = document.getElementById('end-date');
	const ddElem = document.getElementById('dd');

	const startDate = new Date(startDateElem.value);
	const endDate = new Date(endDateElem.value);

	// 오늘 날짜를 구함
	const today = new Date();
	today.setHours(0, 0, 0, 0);  // 시간을 00:00:00으로 설정하여 오늘날짜도 가능하게 함

	// 출발일이 오늘 이전인 경우 오류 메시지
	if (startDate < today) {
		ddElem.value = "출발일은 오늘부터 가능합니다.";
		submitBtn.disabled = true;
		return;
	}

	if (endDate < startDate) {
		// 종료일이 시작일 이전일 경우 오류 메시지
		ddElem.value = "종료일은 시작일보다 늦어야 합니다.";
		submitBtn.disabled = true;
		return;
	}

	const diffTime = Math.abs(endDate - startDate);
	const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
	// 하루를 밀리초로 표현한값 = (1000 * 60 * 60 * 24)

	const nights = diffDays;
	const days = diffDays + 1;

	console.log('nights:', nights);
	console.log('days:', days);

	// dd input 필드에 값 업데이트
	ddElem.value = `${nights}박 ${days}일`;

	// 오류가 없으면 완료 버튼 활성화
	submitBtn.disabled = false;

}

/*초기화 버튼 함수*/
function resetForm() {
	            // 출발지 입력란 초기화
	            document.getElementById('departureInput').value = '';			    
	            // 출발시간 입력란 초기화
	            document.getElementById('departureTime').value = '';		
	            //document.getElementById('departimeInput').value = '';	    
	            // 기간 입력란 초기화
	            document.getElementById('start-date').value = '';			
	            document.getElementById('end-date').value = '';	    
	            // 지역 입력란 초기화
	            document.getElementById('areaSelect').value = '';
	            //document.getElementById('areaInput').value = '';
				// 일정 입력란 초기화
				document.getElementById('dd').value = '';
	        }