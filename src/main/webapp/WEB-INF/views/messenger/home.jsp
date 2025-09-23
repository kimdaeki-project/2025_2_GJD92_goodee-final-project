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

<style>
    body {
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
        height: 100vh;
        display: flex;
    }
    /* 좌측 사이드바 */
    .sidebar {
        width: 60px;
        border-right: 1px solid #eee;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px 0;
        box-sizing: border-box;
    }
    .sidebar .material-icons {
        font-size: 28px;
        margin: 20px 0;
        cursor: pointer;
        color: #444;
        transition: color 0.2s;
    }
    .sidebar .material-icons:hover {
        color: #000;
    }
    .sidebar .active {
        color: #000;
    }

    /* 메인 컨텐츠 */
    .main {
        flex: 1;
        padding: 20px;
        box-sizing: border-box;
        overflow-y: auto;
    }
    .section-title {
        font-size: 14px;
        font-weight: bold;
        margin: 20px 0 10px 0;
        color: #333;
    }
    .member {
        display: flex;
        align-items: center;
        padding: 10px 0;
        cursor: pointer;
    }
    .member img {
        width: 42px;
        height: 42px;
        border-radius: 50%;
        border: 1px solid #ccc;
        object-fit: cover;
        margin-right: 12px;
    }
    .member-info {
        display: flex;
        flex-direction: column;
    }
    .member-name {
        font-size: 14px;
        font-weight: bold;
        color: #000;
    }
    .member-role {
        font-size: 13px;
        color: #888; /* 회색으로 직급 표시 */
    }
    /* 내 프로필 아래 구분선 */
    .my-profile {
        border-bottom: 1px solid #ddd;
        padding-bottom: 12px;
        margin-bottom: 20px;
    }
</style>
</head>
<body>
    <!-- 좌측 사이드바 -->
    <div class="sidebar">
        <span class="material-icons active">person_outline</span>
        <a href="/msg/room"><span class="material-icons">chat_bubble_outline</span></a>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="main">
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