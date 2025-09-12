<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
</head>
<body>
	<h1>공지</h1>
	<div>
<%-- 		<c:forEach items="${ notice }" var="n">
			<div>
				<span>번호: ${ n.noticeNum } | 작성자: ${ n.staffDTO.staffName} | 제목: ${ n.noticeTitle } | 작성일: ${ n.noticeDate } | 조회수: ${ n.noticeHits }</span>
			</div>
		</c:forEach> --%>
		<span>${ notice.content }</span>
	</div>
	<a href="/notice/write">작성</a>
</body>
</html>