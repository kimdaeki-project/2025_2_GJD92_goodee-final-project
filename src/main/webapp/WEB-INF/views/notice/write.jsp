<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
</head>
<body>
	<h1>쓰기</h1>
	<form action="./write" method="post">
		<div>
			<label for="noticeTitle">제목</label>
			<input type="text" id="noticeTitle" name="noticeTitle" value="${ notice.noticeTitle }">
		</div>
		<div>
			<label for="noticeContent">내용</label>
			<textarea rows="10" cols="20" id="noticeContent" name="noticeContent">${ notice.noticeContent }</textarea>
		</div>
		<div>
			<label for="noticePinned">상단고정</label>
			<input type="checkbox" id="noticePinned" name="noticePinned">
		</div>
		<c:if test="${ notice eq null }">
			<button>등록</button>
		</c:if>
		<c:if test="${ notice ne null }">
			<button>수정</button>
		</c:if>
	</form>
</body>
</html>