<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>어트랙션 목록</title>
</head>
<body>
	<h1>어트랙션 목록</h1>
	<c:forEach items="${ride }" var="ride">
		<div>${ride.rideName }</div>
	</c:forEach>
</body>
</html>