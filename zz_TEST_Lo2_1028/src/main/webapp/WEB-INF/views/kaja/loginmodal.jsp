<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>         
<meta charset="EUC-KR">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<title>Insert title here</title>
</head>
<body> 
	
	<style>
	    /* 모달 스타일 */
	    .modal {
	        display: none; /* 기본적으로 숨김 */
	        position: fixed; /* 화면 고정 */
	        z-index: 1000;
	        left: 0;
	        top: 0;
	        width: 100%;
	        height: 100%;
	        overflow: auto;
	        background-color: rgb(0,0,0);
	        background-color: rgba(0,0,0,0.4); /* 배경 반투명검은색으로 설정  */
	    }
	    .modal-content {
	        background-color: #fefefe;
	        margin: 15% auto; /* 화면 중앙 위치 */
	        padding: 20px;
	        border: 1px solid #888; /* 모달 외곽선 스타일 설정 */
	        width: 80%; /* 모달의 너비 */
	    }
	    .close {
	        color: #aaa;
	        float: right; /* 버튼 오른쪽으로 정렬 */
	        font-size: 28px;
	        font-weight: bold;
	    }
	    .close:hover,
	    .close:focus {
	        color: black;
	        text-decoration: none;
	        /* cursor: pointer; /* 마우스 손가락 모양 변경 */ 
			
	    }
	</style>	
	
	<!-- modal.jsp -->
	<div id="myModal" class="modal" style="display:none;">
	    <div class="modal-content">
	        <!--<span class="close">&times;</span>-->
	        <p id="modalMessage"></p>
			<button class="close" id="confirmButton">확인</button> 
	    </div>
	</div>
	<script>
	    $(document).ready(function() {
	        // 로그인 폼 제출 이벤트
	        $('#loginForm').on('submit', function(e) {
	            e.preventDefault(); // 기본 폼 제출 방지
	            
	            const formData = $(this).serialize(); // 폼 데이터 가져오기

	            $.post('/loginHaja2', formData)
	                .done(function(response) {
	                    $('#modalMessage').text("로그인 성공!"); // 모달에 메시지 설정
	                    $('#myModal').show(); // 모달 열기
						// 모달 닫기 및 리디렉션
						// '#confirmButton' -> 새로운 페이지로 리디렉션
						$(document).on('click', '#confirmButton', function() {
							window.location.href = '/kaja/loginwelcome'; // 새로운 페이지로 이동
						});
	                })
	                .fail(function(xhr) {
	                    $('#modalMessage').text(xhr.responseText); // 오류 메시지 모달에 설정
	                    $('#myModal').show(); // 모달 열기
						$('#confirmButton').off('click').on('click', function() {
							window.location.href = '/'; // 오류 시 메인 페이지로 이동
						});
	                });
	        });

	    });
	</script>

</body>
</html>