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
</style>
</head>
<body>
	<h2>메신저</h2>
	<div>
		<a href="/msg/room">채팅방 목록</a>
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
	<script type="text/javascript" src="/js/messenger/connect.js"></script>
</body>
</html>