const swiper = new Swiper('.swiper-container', {
	slidesPerView: 1, // 한 번에 보이는 슬라이드 수
	spaceBetween: 20, // 슬라이드 간 간격
	loop: true, // 루프(무한 회전) 활성화
	centeredSlides: true, // 슬라이드 중앙 정렬
	pagination: { // 페이지네이션 버튼(페이징기법)
		el: '.swiper-pagination', // 페이지 표시기
		clickable: true, // 클릭 가능 설정

	},
	navigation: {
		nextEl: '.swiper-button-next', // 다음 슬라이드로 이동하는 버튼
		prevEl: '.swiper-button-prev', // 이전 슬라이드로 이동하는 버튼
	},
	autoplay: {
		delay: 3000, // 3초마다 자동재생
	}

});
