function handleSubmit(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    // 입력란에서 값 가져오기
    const inputField = document.getElementById('inputField').value.trim();

    // 주문번호 또는 전화번호인지 확인
    if (!isOrderNumber(inputField) && !isPhoneNumber(inputField)) {
        alert("주문번호 또는 전화번호 형식이 잘못되었습니다.");
        return false;
    }

    // 동적으로 폼 생성
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/pay_history';
    form.target = 'recentPayHistory';

    // 주문번호 또는 전화번호를 위한 input 추가
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = isOrderNumber(inputField) ? 'orderNumber' : 'phoneNumber';
    input.value = inputField;
    form.appendChild(input);

    // 폼 제출
    document.body.appendChild(form); // 문서에 폼 추가
    form.submit(); // 폼 제출

    // 폼 제출 후 제거 (옵션)
    document.body.removeChild(form); // 문서에서 폼 제거

    return false; // 폼 제출 방지
}

// 주문번호 형식 확인 (예: order_1729844100836)
function isOrderNumber(value) {
    const orderNumberRegex = /^order_\d{13}$/;  // "order_17298"로 시작하고 뒤에 8자리 숫자
    return orderNumberRegex.test(value);
}

// 전화번호 형식 확인 (예: 010-1234-5678)
function isPhoneNumber(value) {
    const phoneRegex = /^010-\d{4}-\d{4}$/;  // 전화번호 형식 확인 (예: 010-1234-5678)
    return phoneRegex.test(value);
}
