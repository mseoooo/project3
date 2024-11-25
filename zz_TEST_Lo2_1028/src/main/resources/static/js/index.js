function validateForm() {
	// 아이디와 비밀번호 필드를 가져옵니다.
	var id = document.getElementById("id").value;
	var password = document.getElementById("password2").value;

	// 아이디와 비밀번호가 비어 있는지 확인
	if (id === "" || password === "") {
		// 알림을 띄운 후 폼 제출을 막습니다.
		alert("아이디와 비밀번호를 모두 입력해주세요.");
		return false;  // 폼 제출을 막음
	}

	// 입력이 올바르면 폼이 제출됩니다.
	return true;
}