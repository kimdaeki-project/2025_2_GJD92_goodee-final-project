<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
</head>
<body>
	<h4>방 생성</h4>
	<div>
		<a href="/msg">채팅방 목록</a>
	</div>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="logged" />
		<input type="hidden" id="logged" value="${ logged.staffCode }">
	</sec:authorize>
	<form action="/msg/create" method="post" id="form">
		<label for="chatRoomName">채팅방 이름</label>
		<input type="text" name="chatRoomName" id="chatRoomName">
		<input type="hidden" id="addedStaff" name="addedStaff">
	</form>
	<c:forEach items="${ staff }" var="s">
		<div>
			${ s.staffName }
			<button value="${ s.staffCode }" class="add-user">추가</button>
		</div>
	</c:forEach>
	<hr>
	<h4>추가됨</h4>
	<div id="addedUsers"></div>
	<div>
		<button id="createRoom">생성</button>
	</div>
	
	<script type="text/javascript" src="/js/messenger/create.js"></script>
</body>
</html>