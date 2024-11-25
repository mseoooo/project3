// HTML 파일을 동적으로 로드하는 함수
async function loadHTML(id, file) {
	try {
		const response = await fetch(file);
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		const data = await response.text();
		document.getElementById(id).innerHTML = data;
	} catch (error) {
		console.error('Error loading file:', error);
	}
}
