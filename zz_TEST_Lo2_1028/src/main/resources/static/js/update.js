document.getElementById("updateForm").addEventListener("submit", function(event) {
	// 폼 제출 전에 alert 띄우기
	event.preventDefault(); // 폼 제출 막기

	var confirmSave = confirm("변경된 정보를 저장하시겠습니까?");

	if (confirmSave) {
		// 확인 버튼을 누르면 폼 제출
		this.submit();
	} else {
		// 취소 버튼을 누르면 아무 일도 일어나지 않음
		return false;
	}
});