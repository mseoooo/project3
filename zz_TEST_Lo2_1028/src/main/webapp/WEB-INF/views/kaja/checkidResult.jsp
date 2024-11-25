<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 중복 확인 결과</title>
	<link rel="stylesheet" href="/css/checkid.css">
</head>
<body>

<div class="wrapper">
    <h1>아이디 중복 확인 결과</h1>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    
    <c:choose>
        <c:when test="${exists}">
            <p style="color:red;">아이디가 중복되었습니다: ${username}</p>
        </c:when>
        <c:otherwise>
            <p style="color:green;">아이디 사용 가능합니다: ${username}</p>
        </c:otherwise>
    </c:choose>

    <div class="result">
        <a href="/kaja/join?id=${username}">회원가입 페이지로 돌아가기</a>
    </div>
</div>

</body>
</html>


