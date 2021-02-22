<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<h1>Login</h1>
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
		<div>로그인에 실패하였습니다.</div>
		<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />
	</c:if>
	<div>
		<form action="/login" method="post">
			<div>
				<input type="text" name="username" placeholder="id를 입력해주세요."
					value="microform">
			</div>
			<div>
				<input type="password" name="password"
					placeholder="password를 입력해주세요." value="1212">
			</div>
			<button type="submit">로그인</button>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		</form>
	</div>
	
	<div>
		<a href="javascript:;" class="btn_social" data-social="facebook">페이스북 로그인</a>
		<a href="javascript:;" class="btn_social" data-social="google">구글 로그인</a>
		<a href="javascript:;" class="btn_social" data-social="kakao">카카오톡 로그인</a>
		<a href="javascript:;" class="btn_social" data-social="naver">네이버 로그인</a>
	</div>
	
	<div>
		<a href="/join">회원가입</a>
	</div>

	<script> 
		let socials = document.getElementsByClassName('btn_social')
		for(let social of socials) { 
			social.addEventListener('click', function(){ 
				let socialType = this.getAttribute('data-social')
				location.href="/oauth2/authorization/" + socialType 
			}) 
		} 
	</script>
</body>
</html>