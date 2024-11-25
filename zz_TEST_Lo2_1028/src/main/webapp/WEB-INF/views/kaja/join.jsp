<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원가입</title>
<link rel="stylesheet" href="/css/join.css">
<script src="/js/join.js" defer></script>
</head>
<body> 
  
   <form id="join_form" method="post" action='/kaja/memberJoin'>
       <div class="wrapper">
           <script src="/js/join.js" defer></script>
           
           <div class="id_wrap">
               <div class="id_name">아이디</div>
               <div class="id_input_box">
                   <input class="id_input" id="id" name="id" placeholder="example123" required>
                   <button type="button" onclick="checkId()">중복 확인</button>
                   <span id="idCheckResult"></span> <!-- 중복 확인 결과를 출력할 영역 -->
               </div>
           </div>

           <div class="name_wrap">
               <div class="id2_name">이름</div>
               <div class="name_input_box">
                   <input class="name_input" id="name" name="name" placeholder="홍길동" required>
               </div>
           </div>
            <br>
           <div class="email_wrap">
               <div class="email_name">이메일</div>
               <div class="email_input_box">
                   <input type="email" class="email_input" name="email" placeholder="abcd@example.com" required>
               </div>
           </div>
         <br>
           <div class="pw_wrap">
               <div class="pw_name">비밀번호</div>
               <div class="pw_input_box">
                   <input type="password" class="pw_input" name="password2" placeholder="영문대소문자와 특수문자" required>
               </div>
           </div>
         <br>
           <div class="tel_wrap">
               <div class="tel_name">전화번호</div>
               <div class="tel_input_box">
                   <input class="tel_input" name="tel" placeholder="010-1234-5678" 
                       pattern="^010-\d{4}-\d{4}$" title="전화번호 형식은 010-xxxx-xxxx 이어야 합니다." required>
               </div>
           </div>
         <br>
           <div class="birth_wrap">
               <div class="birth_name">생년월일</div>
               <div class="birth_input_box">
                   <input type="date" class="birth_input" name="birth" required>
               </div>
           </div>

         <!-- 회원가입과 뒤로가기 버튼을 나란히 배치할 컨테이너 -->
         <div class="buttons_wrap">
             <div class="join_button_wrap">
                 <input type="submit" value="회원가입">
             </div>

            <div class="back_button_wrap">
                      <button type="button" onclick="window.history.back();">뒤로 가기</button>
                  </div>
         
                    </div>

                </div>
            </form>
     
</body>
</html>