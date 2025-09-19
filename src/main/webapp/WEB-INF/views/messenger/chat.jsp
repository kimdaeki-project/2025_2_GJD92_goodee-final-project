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
</head>
<body>
	<h2>메신저</h2>
	<div>
		<a href="/msg">채팅방 목록</a>
	</div>
	<input type="text" id="messageInput" placeholder="메시지를 입력하세요" />
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="staff" />
		<input type="hidden" id="messageSender" value="${ staff.staffCode }">
		<input type="hidden" id="messageSenderName" value="${ staff.staffName }">
	</sec:authorize>
	<button id="sendButton">전송</button>
	<input type="hidden" id="chatRoomNum" value="${ chatRoomNum }">
	<div id="messages">
		<c:forEach items="${ chat }" var="c">
			<div>${ c.staffName }: ${ c.chatBodyContent }(${ c.chatBodyDtm })</div>
		</c:forEach>
	</div>
	<script type="text/javascript">
		const chatRoomNum = document.querySelector('#chatRoomNum').value;
	    const socket = new SockJS('http://192.168.1.35/ws-stomp');
	    const stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        console.log('Connected: ' + frame);
	        
	        stompClient.subscribe('/sub/chat' + chatRoomNum, function (message) { // 구독 경로
	            const receivedMessage = JSON.parse(message.body); // 메시지 파싱
	            displayMessage(receivedMessage); // 화면에 메시지 표시
	        });

	        document.getElementById('sendButton').addEventListener('click', function () {
	            const contents = document.querySelector('#messageInput').value;
	            const sender = document.querySelector('#messageSender').value;
	            const senderName = document.querySelector('#messageSenderName').value;
	            const message = {
	                type: "SEND", // 고정된 type
	                contents: contents, // 입력된 내용
	                chatRoomNum: chatRoomNum,
	                staffCode: sender,
					chatBodyContent: contents,
					staffName: senderName
					// 날짜, 삭제 여부는 컨트롤러에서 세팅
	            };
	            stompClient.send("/pub/chat" + chatRoomNum, {}, JSON.stringify(message)); // 메세지 전송 경로
	            document.getElementById('messageInput').value = ''; // 입력 필드 초기화
	        });
	    });
	    // 화면에 메시지를 추가하는 함수
	    function displayMessage(message) {
	        const messagesDiv = document.getElementById('messages');
	        const messageElement = document.createElement('div');
	        messageElement.textContent = message.staffName + ': ' + message.chatBodyContent + '(' + message.chatBodyDtm + ')'; // 메시지 내용 설정
	        messagesDiv.appendChild(messageElement); // 메시지 추가
	        messagesDiv.scrollTop = messagesDiv.scrollHeight; // 스크롤을 맨 아래로
	    }
	</script>
</body>
</html>