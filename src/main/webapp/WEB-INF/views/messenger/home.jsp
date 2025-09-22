<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
</head>
<body>
	<h4>멤버</h4>
    <sec:authorize access="isAuthenticated()">
       	<sec:authentication property="principal" var="staff" />
       	<div onclick="profile(this)" style="cursor: pointer;">
   			<form action="/msg/profile"><input type="hidden" name="staffCode" value="${ staff.staffCode }"></form>
	       	<img width="35" height="35" style="padding: 1px; border: 1px solid #686868" alt="" src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }">
   			<span class="ms-2 align-middle" style="height: 25px; line-height: 25px;">${ staff.deptDTO.deptDetail }부서 ${ staff.jobDTO.jobDetail } ${ staff.staffName }</span>
       	</div>
    </sec:authorize>
   	<hr>
   	<c:forEach items="${ members }" var="m">
   		<div onclick="profile(this)" style="cursor: pointer;">
   			<form action="/msg/profile"><input type="hidden" name="staffCode" value="${ m.staffCode }"></form>
	       	<img width="35" height="35" style="padding: 1px; border: 1px solid #686868" alt="" src="/file/staff/${ m.staffAttachmentDTO.attachmentDTO.savedName }">
   			<span class="ms-2 align-middle" style="height: 25px; line-height: 25px;">${ m.deptDTO.deptDetail }부서 ${ m.jobDTO.jobDetail } ${ m.staffName }</span>   			
   		</div>
   	</c:forEach>
	<a href="/msg/room">채팅방</a>
	
	<script type="text/javascript">
		function profile(e) {
			const form = e.firstElementChild;
			form.submit();
		}
	</script>
</body>
</html>