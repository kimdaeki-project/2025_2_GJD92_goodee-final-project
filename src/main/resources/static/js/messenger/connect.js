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
//화면에 메시지 출력하기
function displayMessage(message) {
    const messagesDiv = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.textContent = message.staffName + ': ' + message.chatBodyContent;
    messagesDiv.appendChild(messageElement);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}
// 무한 스크롤 구현하기
const messageBox = document.querySelector('#messages');
let next = document.querySelector('#next').value;
let page = document.querySelector('#page').value;
messageBox.addEventListener('scroll', () => {
	if (messageBox.scrollTop === 0 && next) {
		loadMessages();
	}
});

function loadMessages() {
	page++;
	fetch('/msg/load', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({
			page: page,
			chatRoomNum: chatRoomNum
		})
	})
	.then(response => response.json())
	.then(response => {
		const oldHeight = messageBox.scrollHeight;
		
		response.messages.forEach(msg => {
			const div = document.createElement('div');
			div.textContent = msg.staffName + ': ' + msg.chatBodyContent;
			messageBox.prepend(div);
		});
		
		const newHeight = messageBox.scrollHeight;
		messageBox.scrollTop = newHeight - oldHeight;
		next = response.next;
	});
}