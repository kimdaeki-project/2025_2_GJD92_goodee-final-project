<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
<script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link href="/css/messenger/chat.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.9.3/dist/confetti.browser.min.js"></script>
</head>
<body>
	<% pageContext.setAttribute("br", "\n"); %>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="staff" />
		<input type="hidden" id="messageSender" value="${ staff.staffCode }">
		<input type="hidden" id="messageSenderName" value="${ staff.staffName }">
		<input type="hidden" id="next" value="${ next }">
		<input type="hidden" id="page" value="0">
	</sec:authorize>
	<c:if test="${ chatRoom.chatRoomGroup ne true }">
		<c:forEach items="${ chatRoom.chatUserDTOs }" var="s">
			<c:if test="${ s.staffDTO.staffCode ne staff.staffCode }">
				<div class="profile-box">
					<img class="profile-img" src="/file/staff/${ s.staffDTO.staffAttachmentDTO.attachmentDTO.savedName }">
					<h2 class="profile-name">${ s.staffDTO.staffName }</h2>
				</div>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${ chatRoom.chatRoomGroup eq true }">
		<div class="profile-box">
			<img class="profile-img" src="/images/heartBeat/groupChat.png">
			<h2 class="profile-name">${ chatRoom.chatRoomName }</h2>		
		</div>
	</c:if>
	<div class="chat-top-group">
		<div>
			<a href="/msg/room/all" class="chat-list-anchor">채팅방 목록</a>
			<c:if test="${ chatRoom.chatRoomGroup eq true }">
				&nbsp;
				<span class="chat-list-anchor group-member-list">멤버 목록</span>
			</c:if>
		</div>
		<div>
			<c:if test="${ chatRoom.chatRoomGroup eq true }">
				<span id="addMemeber" class="material-icons" style="cursor: pointer;">person_add</span>
				<span id="chat-leave" class="material-icons" style="cursor: pointer;">logout</span>
			</c:if>
		</div>
	</div>
	<input type="hidden" id="chatRoomNum" value="${ chatRoomNum }">
	<hr class="divider">
	<div id="messages">
		<c:forEach items="${ chat }" var="c">
		    <c:if test="${ c.chatBodyType eq 'SEND' }">
		      	<div class="chat-message <c:if test='${ c.staffCode eq staff.staffCode }'>me</c:if>">
		        	<div class="chat-sender">${ c.staffName }</div>
		        	<div class="chat-text-wrapper">
		            	<div class="chat-text">${fn:replace(c.chatBodyContent, br, '<br>')}</div>
		            	<div class="chat-meta">
				            <div class="chat-date-inline">${ c.chatDate }</div>
				            <div class="chat-time">${ c.chatTime }</div>
		            	</div>
		        	</div>
		      	</div>
		    </c:if>
	    	<c:if test="${ c.chatBodyType eq 'NEW' }">
	    		<div class="chat-read-divider">${ c.chatBodyContent }</div>
	    	</c:if>
		</c:forEach>
	</div>
	<div class="message-container-chat">
	    <textarea rows="3" cols="30" id="messageInput" placeholder="메시지를 입력해 주세요."></textarea>
	    <div class="button-wrapper">
	        <button id="sendButton" disabled>전송</button>
	    </div>
	</div>

	
	<!-- 멤버 추가 모달 -->
	<div id="addMemberModal">
		<div id="addMemberModalInternal">
			<h3>멤버 초대</h3>
			<div id="selectedMembers"></div>
			<div id="memberList"></div>
			<div style="text-align:right; margin-top:10px;" class="modal-btn-group">
				<button id="closeModal">취소</button>
				<button id="addMembers">추가</button>
			</div>
		</div>
	</div>
	
	<div id="viewGroupMembers">
		<div id="viewGroupMembersInternal">
			<div class="group-member-count">
				<h4>채팅방 참여 인원</h4>
				<div style="display: flex; align-items: center;">
					<span class="material-icons">account_circle</span>
					<span class="group-member-count-span"></span>
				</div>
			</div>
			<div class="group-member-view-list">
				<div class="group-member-card">
		            <img src="/file/staff/${ staff.staffAttachmentDTO.attachmentDTO.savedName }" class="group-profile-img">
		            <div class="group-member-info">
		                <div class="group-member-name">${ staff.staffName }</div>
		                <div class="group-member-position">${ staff.jobDTO.jobDetail }</div>
		            </div>
		        </div>
		        <hr class="divider" style="width: 100%">
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/js/messenger/connect.js"></script>
	<c:if test="${ chatRoom.chatRoomGroup eq true }">	
		<script type="text/javascript" src="/js/messenger/connect-group.js"></script>
	</c:if>
</body>
</html>