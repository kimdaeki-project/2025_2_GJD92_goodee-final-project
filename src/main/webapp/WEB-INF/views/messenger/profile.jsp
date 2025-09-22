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
	<h4>프로필</h4>
   	<img width="60" height="60" style="padding: 1px; border: 1px solid #686868" alt="" src="/file/staff/${ profile.staffAttachmentDTO.attachmentDTO.savedName }">
   	<div>
   		<span>이름: ${ profile.staffName }</span>
   	</div>
   	<div>
   		<span>부사: ${ profile.deptDTO.deptDetail }</span>
   	</div>
   	<div>
   		<span>직급: ${ profile.jobDTO.jobDetail }</span>
   	</div>
  	<div>
   		<span>이메일: ${ profile.staffEmail }</span>
   	</div>
  	<div>
   		<span>번호: ${ profile.staffPhone }</span>
   	</div>
  	<sec:authorize access="isAuthenticated()">
       	<sec:authentication property="principal" var="staff" />
		<c:if test="${ staff.staffCode ne profile.staffCode }">
			<form action="/msg/profile/chat" method="post">
				<input type="hidden" name="staffCode" value="${ profile.staffCode }">
				<button>메시지 전송</button>
			</form>
		</c:if>
    </sec:authorize>
   	<div>
   		<a href="/msg">멤버</a>
   	</div>
</body>
</html>