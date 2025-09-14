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
		<c:forEach items="${ notice.content }" var="n">
			<div>
				<span>번호: ${ n.noticeNum } | 작성자: ${ n.staffDTO.staffName } | <a href="/notice/${ n.noticeNum }">제목: ${ n.noticeTitle }</a> | 작성일: ${ n.noticeDate } | 조회수: ${ n.noticeHits }</span>
			</div>
		</c:forEach>
		<c:if test="${ notice.hasPrevious()}">
    		<a href="?page=${ notice.number - 1 }">이전</a>
		</c:if>
		<c:forEach var="i" begin="0" end="${ notice.totalPages - 1}">
    		<a href="?page=${i}" style="${i == notice.number ? 'font-weight:bold;' : ''}">
        		${i + 1}
    		</a>
		</c:forEach>
		<c:if test="${ notice.hasNext()}">
    		<a href="?page=${ notice.number + 1 }">다음</a>
		</c:if>
	</div>
	<a href="/notice/write">작성</a>
</body>
</html>