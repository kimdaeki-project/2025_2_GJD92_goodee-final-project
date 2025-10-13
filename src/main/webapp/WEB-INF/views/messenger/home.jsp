<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
<!-- 구글 머티리얼 아이콘 -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="/css/messenger/home.css" rel="stylesheet">
</head>
<body>
    <!-- 좌측 사이드바 -->
    <div class="sidebar">
        <span class="material-icons active">person_outline</span>
        <a href="/msg/room/all"><span class="material-icons">chat_bubble_outline</span></a>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="main">
    	<form action="/msg" method="get" class="search-form">
	    	<input type="text" placeholder="이름을 입력하세요" name="keyword" value="${ keyword }">
	    	<button>검색</button>
    	</form>
        <h3>메신저</h3>
        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal" var="staff" />
            <div class="section-title">내 프로필</div>
            <div class="member my-profile" onclick="profile(this)">
                <form action="/msg/profile"><input type="hidden" name="staffCode" value="${ staff.staffCode }"></form>
                <img alt="" src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }">
                <div class="member-info">
                    <div class="member-name">${ staff.staffName }</div>
                    <div class="member-role">${ staff.deptDTO.deptDetail } · ${ staff.jobDTO.jobDetail }</div>
                </div>
            </div>
        </sec:authorize>

        <div class="section-title">임원</div>
        <c:forEach items="${ members }" var="m">
            <c:if test="${ m.deptDTO.deptName eq 'ROLE_HQ' }">
                <div class="member" onclick="profile(this)">
                    <form action="/msg/profile"><input type="hidden" name="staffCode" value="${ m.staffCode }"></form>
                    <img alt="" src="/file/staff/${ m.staffAttachmentDTO.attachmentDTO.savedName }">
                    <div class="member-info">
                        <div class="member-name">${ m.staffName }</div>
                        <div class="member-role">${ m.jobDTO.jobDetail }</div>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <div class="section-title">인사팀</div>
        <c:forEach items="${ members }" var="m">
            <c:if test="${ m.deptDTO.deptName eq 'ROLE_HR' }">
                <div class="member" onclick="profile(this)">
                    <form action="/msg/profile"><input type="hidden" name="staffCode" value="${ m.staffCode }"></form>
                    <img alt="" src="/file/staff/${ m.staffAttachmentDTO.attachmentDTO.savedName }">
                    <div class="member-info">
                        <div class="member-name">${ m.staffName }</div>
                        <div class="member-role">${ m.jobDTO.jobDetail }</div>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <div class="section-title">운영팀</div>
        <c:forEach items="${ members }" var="m">
            <c:if test="${ m.deptDTO.deptName eq 'ROLE_OP' }">
                <div class="member" onclick="profile(this)">
                    <form action="/msg/profile"><input type="hidden" name="staffCode" value="${ m.staffCode }"></form>
                    <img alt="" src="/file/staff/${ m.staffAttachmentDTO.attachmentDTO.savedName }">
                    <div class="member-info">
                        <div class="member-name">${ m.staffName }</div>
                        <div class="member-role">${ m.jobDTO.jobDetail }</div>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <div class="section-title">시설팀</div>
        <c:forEach items="${ members }" var="m">
            <c:if test="${ m.deptDTO.deptName eq 'ROLE_FA' }">
                <div class="member" onclick="profile(this)">
                    <form action="/msg/profile"><input type="hidden" name="staffCode" value="${ m.staffCode }"></form>
                    <img alt="" src="/file/staff/${ m.staffAttachmentDTO.attachmentDTO.savedName }">
                    <div class="member-info">
                        <div class="member-name">${ m.staffName }</div>
                        <div class="member-role">${ m.jobDTO.jobDetail }</div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>

    <script type="text/javascript" src="/js/messenger/home.js"></script>
</body>
</html>