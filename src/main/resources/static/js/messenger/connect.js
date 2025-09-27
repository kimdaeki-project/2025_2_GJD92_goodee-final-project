/**
 * chat.jsp 랑 연동
 * 채팅, 화면에 메시지 출력, 메시지 DB에 저장, 무한 스크롤 로직 스크립트
 */
const chatRoomNum = document.querySelector('#chatRoomNum').value;
const sender = document.querySelector('#messageSender').value;
const senderName = document.querySelector('#messageSenderName').value;
const socket = new SockJS('http://localhost/ws-stomp');
const stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame); // 커넥션 콘솔에 표시
    
    stompClient.subscribe('/sub/chat/' + chatRoomNum, function (message) { // 구독 경로
        const receivedMessage = JSON.parse(message.body); // 메시지 파싱
        displayMessage(receivedMessage); // 화면에 메시지 표시
    });
	
	// 채팅방에 들어왔을 때 읽은 메시지의 개수를 푸터의 읽지않은 메시지 개수에서 빼주는 로직의 함수
	syncUnreadCount();

    document.getElementById('sendButton').addEventListener('click', function () {
        const contents = document.querySelector('#messageInput').value;
        const message = {
            type: "SEND", // 고정된 type
            contents: contents,
            chatRoomNum: chatRoomNum,
            staffCode: sender,
			chatBodyContent: contents,
			staffName: senderName
			// 날짜, 삭제 여부는 컨트롤러에서 세팅
        };
        stompClient.send("/pub/chat/" + chatRoomNum, {}, JSON.stringify(message));
		fetch('/msg/notify', {
			method: 'post',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ chatRoomNum: chatRoomNum })
		})
		.then(response => response.json())
		.then(response => {
			response.forEach(el => {
				if (el.staffDTO.staffCode != sender) {
					let notification = {
						type: 'CHATCOUNT',
						msg: 'plus'
					}
			        stompClient.send("/pub/notify/" + el.staffDTO.staffCode, {}, JSON.stringify(notification));					
				}
			});
		});
        document.getElementById('messageInput').value = '';
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
// 무한 스크롤 메시지 가져오기
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
// 안 읽은 메시지 수
window.addEventListener("beforeunload", function() {
	const formData = new FormData();
	formData.append("chatRoomNum", chatRoomNum);
	navigator.sendBeacon("/msg/exit", formData);
});
// 안읽은 메시지 제거
function syncUnreadCount() {
	stompClient.send("/pub/notify/" + sender, {}, JSON.stringify({msg: chatRoomNum, type: 'SYNCCHATCOUNT'}));
}