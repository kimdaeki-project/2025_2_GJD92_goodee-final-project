<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	<input type="text" id="messageInput" placeholder="메시지를 입력하세요" />
	<sec:authentication property="principal" var="staff" />
		<input type="hidden" id="messageSender" value="${ staff.staffName }">
	<sec:authorize access="isAuthenticated()"></sec:authorize>
	<button id="sendButton">전송</button>
	<div id="messages"></div>
	<script type="text/javascript">
	    const socket = new SockJS('http://192.168.1.35/ws-stomp');
	    const stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        console.log('Connected: ' + frame);
	        
	        stompClient.subscribe('/sub', function (message) { // 구독 경로
	            const receivedMessage = JSON.parse(message.body); // 메시지 파싱
	            displayMessage(receivedMessage); // 화면에 메시지 표시
	        });

	        document.getElementById('sendButton').addEventListener('click', function () {
	            const contents = document.querySelector('#messageInput').value;
	            const sender = document.querySelector('#messageSender').value;
	            const message = {
	                type: "SEND", // 고정된 type
	                contents: contents, // 입력된 내용
	                sender: sender
	            };
	            stompClient.send("/pub/send", {}, JSON.stringify(message)); // 메세지 전송 경로
	            document.getElementById('messageInput').value = ''; // 입력 필드 초기화
	        });
	    });
	    // 화면에 메시지를 추가하는 함수
	    function displayMessage(message) {
	        const messagesDiv = document.getElementById('messages');
	        const messageElement = document.createElement('div');
	        messageElement.textContent = message.sender + ': ' + message.contents; // 메시지 내용 설정
	        messagesDiv.appendChild(messageElement); // 메시지 추가
	        messagesDiv.scrollTop = messagesDiv.scrollHeight; // 스크롤을 맨 아래로
	    }
	</script>
</body>
</html>