const bars = document.querySelector(".fas");
const minimenu = document.querySelector("#minimenu");

function onClick() {
	minimenu.classList.toggle("active"); // 'active' 클래스를 토글
}

bars.addEventListener("click", onClick);