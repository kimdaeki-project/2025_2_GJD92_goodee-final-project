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
<link href="/css/messenger/list.css" rel="stylesheet">
</head>
<body>
    <form action="/msg/room/all" method="get" id="formAll"></form>
    <form action="/msg/room/dm" method="get" id="formDm"></form>
    <form action="/msg/room/group" method="get" id="formGroup"></form>
    <!-- 좌측 사이드바 -->
    <div class="sidebar">
        <a href="/msg"><span class="material-icons">person_outline</span></a>
        <span class="material-icons active">chat_bubble_outline</span>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="main">
        <!-- 헤더 -->
        <div class="header">
            <h3>채팅</h3>
            <a href="/msg/create"><span class="material-icons">add</span></a>
        </div>

        <!-- 탭 메뉴 -->
        <div class="tabs">
            <div class="tab tab-all <c:if test="${ active eq 'all' }"> active</c:if>">전체</div>
            <div class="tab tab-dm <c:if test="${ active eq 'dm' }"> active</c:if>">개인</div>
            <div class="tab tab-group <c:if test="${ active eq 'group' }"> active</c:if>">그룹</div>
        </div>

        <!-- 채팅방 리스트 -->
        <c:if test="${ not empty room }">
        <c:forEach items="${ room }" var="r">
            <form action="/msg/chat" method="post" class="chat-room">
                <input type="hidden" name="chatRoomNum" value="${ r.chatRoomNum }">
                <div class="chat-room-info">
                	<c:if test="${ r.chatRoomName eq 'DM_NONAME' }">
	                	<sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal" var="staff" />
		                	<c:forEach items="${ r.chatUserDTOs }" var="s">
		                		<c:if test="${ s.staffDTO.staffCode ne staff.staffCode }">
			                    	<div class="chat-room-name">${ s.staffDTO.staffName }</div>
			                    </c:if>               	
		                	</c:forEach>
	                	</sec:authorize>
                	</c:if>
                	<c:if test="${ r.chatRoomName ne 'DM_NONAME' }">
                		<div class="chat-room-name">
                			${ r.chatRoomName }
                		</div>
                	</c:if>
                    <div id="chat-room-last-${ r.chatRoomNum }" class="chat-room-last"></div>
                </div>
                <div class="chat-room-right">
                    <span class="time" id="time-${ r.chatRoomNum }"></span>
                    <span id="unread-count-${ r.chatRoomNum }" class="badge" style="color: red; font-weight: bold;"></span>
                </div>
            </form>
        </c:forEach>
        </c:if>
        <c:if test="${ empty room }">
        	<div class="no-room">참여중인 채팅방이 없습니다</div>
        </c:if>
    </div>

    <script>	
        const rooms = [
            <c:forEach var="room" items="${ room }" varStatus="status">
                ${ room.chatRoomNum }<c:if test="${ !status.last }">,</c:if>
            </c:forEach>
        ];
    </script>
    <script type="text/javascript" src="/js/messenger/list.js"></script>
    <c:import url="/WEB-INF/views/messenger/heartBeat.jsp"/>
</body>
</html>