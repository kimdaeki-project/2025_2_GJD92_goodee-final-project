<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메신저</title>
<script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
<style>
  /* 채팅 영역 */
  #messages {
    width: 100%;                /* 필요에 따라 조정 */
    height: 300px;              /* 원하는 채팅창 높이 */
    overflow-y: auto;           /* 세로 스크롤 */
    border: 1px solid #ccc;     /* 구분선 */
    padding: 10px;
    box-sizing: border-box;
    background-color: #fafafa;  /* 보기 좋게 배경색 */
  }

  /* 메시지 스타일 */
  #messages div {
    margin: 5px 0;
    padding: 4px 8px;
    border-radius: 4px;
    background: #fff;
  }
  
  #addMemberModal {
	display:none; 
	position:fixed; 
	top:0; 
	left:0; 
	width:100%; 
	height:100%; 
	background:rgba(0,0,0,0.5); 
	justify-content:center; 
	align-items:center;
  }
  
  #addMemberModalInternal {
  	background:#fff; 
  	padding:20px; 
  	border-radius:8px;
  	width:400px; 
  	max-width:70%;
  }
  
  #memberList {
  	max-height:200px; 
  	overflow-y:auto; 
  	padding:5px;
  }
  
  /* 좌측 멤버 리스트 */
.member-list {
    flex: 2;
    border-right: 1px solid #eee;
    padding: 20px;
    overflow-y: auto;
}

.section-title {
    font-size: 14px;
    font-weight: bold;
    margin: 20px 0 10px 0;
    color: #333;
}

.member-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 0;
    cursor: pointer;
    transition: background 0.2s;
}

.member-item:hover {
    background: #f9f9f9;
}

.member-info {
    display: flex;
    align-items: center;
    gap: 10px;
}

.member-info img {
    width: 42px;
    height: 42px;
    border-radius: 50%;
    border: 1px solid #ccc;
    object-fit: cover;
}

.member-name {
    font-size: 14px;
    font-weight: bold;
    color: #000;
}

.member-role {
    font-size: 13px;
    color: #888;
}

/* 체크박스 */
.member-item input[type="checkbox"] {
    width: 16px;
    height: 16px;
    cursor: pointer;
    accent-color: #000; /* 체크박스 색상 */
}


/* 선택된 멤버 영역 */
#selectedMembers {
    display: flex;
    gap: 10px;
    margin: 10px 0;
    flex-wrap: wrap;
}

/* 선택된 멤버 아이템 */
.selected-member {
    display: flex;
    align-items: center;
    gap: 5px;
    background: #f9f9f9;
    padding: 4px 8px;
    border-radius: 20px;
    border: 1px solid #ddd;
}

/* 선택된 멤버 프로필 이미지 */
.selected-img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    border: 1px solid #ccc;
    object-fit: cover;
}

/* 선택된 멤버 이름 */
.selected-name {
    font-size: 13px;
    color: #333;
}
</style>
</head>
<body>
	<h2>메신저</h2>
	<div>
		<a href="/msg/room">채팅방 목록</a>
	</div>
	<div>
		<div id="addMemeber">멤버 추가</div>
	</div>
	<input type="text" id="messageInput" placeholder="메시지를 입력하세요" />
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="staff" />
		<input type="hidden" id="messageSender" value="${ staff.staffCode }">
		<input type="hidden" id="messageSenderName" value="${ staff.staffName }">
		<input type="hidden" id="next" value="${ next }">
		<input type="hidden" id="page" value="0">
	</sec:authorize>
	<button id="sendButton">전송</button>
	<input type="hidden" id="chatRoomNum" value="${ chatRoomNum }">
	<div id="messages">
		<c:forEach items="${ chat }" var="c">
			<div>${ c.staffName }: ${ c.chatBodyContent }</div>
		</c:forEach>
	</div>
	
	<!-- 멤버 추가 모달 -->
	<div id="addMemberModal">
		<div id="addMemberModalInternal">
			<h3>멤버 초대</h3>
			<div id="selectedMembers"></div>
			<div id="memberList"></div>
			<div style="text-align:right; margin-top:10px;">
				<button id="closeModal">취소</button>
				<button id="addMembers">추가</button>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/js/messenger/connect.js"></script>
</body>
</html>