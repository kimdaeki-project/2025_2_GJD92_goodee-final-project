<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
<!-- 구글 머티리얼 아이콘 -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="/css/messenger/profile.css" rel="stylesheet">
</head>
<body>
    <div class="profile-wrapper">
        <img alt="프로필 이미지" src="/file/staff/${ profile.staffAttachmentDTO.attachmentDTO.savedName }">

        <div class="profile-name">${ profile.staffName }</div>
        <div class="profile-role">${ profile.deptDTO.deptDetail } · ${ profile.jobDTO.jobDetail }</div>

        <div class="profile-info">
            <span class="material-icons">call</span> ${ profile.staffPhone }
        </div>
        <div class="profile-info">
            <span class="material-icons">email</span> ${ profile.staffEmail }
        </div>

        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal" var="staff" />
            <c:if test="${ staff.staffCode ne profile.staffCode }">
                <form action="/msg/profile/chat" method="post">
                    <input type="hidden" name="staffCode" value="${ profile.staffCode }">
                    <button type="submit" class="profile-button">메시지 전송</button>
                </form>
            </c:if>
        </sec:authorize>

        <a href="/msg" class="back-link">멤버 목록</a>
    </div>
</body>
</html>