
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
	// 채팅방에 들어왔을 때는 읽지 않은 메시지의 개수를 증가시키지 않도록 하는 로직의 함수
	noUnreadCount();

    document.getElementById('sendButton').addEventListener('click', function () {
        const contents = document.querySelector('#messageInput').value;
        const message = {
            chatBodyType: "SEND", // 고정된 type
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
			        stompClient.send("/pub/notify/" + el.staffDTO.staffCode, {}, JSON.stringify({ msg: chatRoomNum, type: 'CHATCOUNT' }));					
				}
			});
		});
        document.getElementById('messageInput').value = '';
    });
});
//화면에 메시지 출력하기
function displayMessage(msg) {
    const messagesDiv = document.getElementById('messages');
	if(msg.chatBodyType == 'SEND') {
		let timeFromJava = msg.chatBodyDtm;
		let timeToJs = new Date(timeFromJava);
		
		let div = document.createElement('div');
		div.classList.add('chat-message');
		if (msg.staffCode == sender) div.classList.add('me');
		let divSender = document.createElement('div');
		divSender.classList.add('chat-sender');
		divSender.innerText = msg.staffName;
		let divTextWrapper = document.createElement('div');
		divTextWrapper.classList.add('chat-text-wrapper');
		let divChatText = document.createElement('div');
		divChatText.classList.add('chat-text');
		divChatText.innerText = msg.chatBodyContent;
		let divChatMeta = document.createElement('div');
		divChatMeta.classList.add('chat-meta');
		let divChatDate = document.createElement('div');
		divChatDate.classList.add('chat-date-inline');
		divChatDate.innerText = timeToJs.getMonth() + 1 + '월 ' + timeToJs.getDate() + '일';
		let divChatTime = document.createElement('chat-time');
		divChatTime.classList.add('chat-time');
		if (timeToJs.getMinutes() < 10) divChatTime.innerText = timeToJs.getHours() + ':0' + timeToJs.getMinutes();
		else divChatTime.innerText = timeToJs.getHours() + ':' + timeToJs.getMinutes();
		
		divChatMeta.appendChild(divChatDate);
		divChatMeta.appendChild(divChatTime);
		divTextWrapper.appendChild(divChatText);
		divTextWrapper.appendChild(divChatMeta);
		div.appendChild(divSender);
		div.appendChild(divTextWrapper);
		messagesDiv.appendChild(div);
	} else if (msg.chatBodyType == 'NEW') {
		let div = document.createElement('div');
		div.classList.add('chat-read-divider');
		div.textContent = msg.chatBodyContent;
		messagesDiv.appendChild(div);	
	}
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
			renderHtmlInfiniteScroll(msg);
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
	formData.append("staffCode", sender);
	navigator.sendBeacon("/msg/exit", formData);
});
// 안읽은 메시지 제거
function syncUnreadCount() {
	stompClient.send("/pub/notify/" + sender, {}, JSON.stringify({msg: chatRoomNum, type: 'SYNCCHATCOUNT'}));
}
// 안읽은 메시지 count X
function noUnreadCount() {
	stompClient.send("/pub/notify/" + sender, {}, JSON.stringify({msg: chatRoomNum, type: 'NOUNREADCOUNT'}));
}
//무한 스크롤 html 렌더링
function renderHtmlInfiniteScroll(msg) {
	if(msg.chatBodyType == 'SEND') {
		let timeFromJava = msg.chatBodyDtm;
		let timeToJs = new Date(timeFromJava);
		
		let div = document.createElement('div');
		div.classList.add('chat-message');
		if (msg.staffCode == sender) div.classList.add('me');
		let divSender = document.createElement('div');
		divSender.classList.add('chat-sender');
		divSender.innerText = msg.staffName;
		let divTextWrapper = document.createElement('div');
		divTextWrapper.classList.add('chat-text-wrapper');
		let divChatText = document.createElement('div');
		divChatText.classList.add('chat-text');
		divChatText.innerText = msg.chatBodyContent;
		let divChatMeta = document.createElement('div');
		divChatMeta.classList.add('chat-meta');
		let divChatDate = document.createElement('div');
		divChatDate.classList.add('chat-date-inline');
		divChatDate.innerText = timeToJs.getMonth() + 1 + '월 ' + timeToJs.getDate() + '일';
		let divChatTime = document.createElement('chat-time');
		divChatTime.classList.add('chat-time');
		if (timeToJs.getMinutes() < 10) divChatTime.innerText = timeToJs.getHours() + ':0' + timeToJs.getMinutes();
		else divChatTime.innerText = timeToJs.getHours() + ':' + timeToJs.getMinutes();
		
		divChatMeta.appendChild(divChatDate);
		divChatMeta.appendChild(divChatTime);
		divTextWrapper.appendChild(divChatText);
		divTextWrapper.appendChild(divChatMeta);
		div.appendChild(divSender);
		div.appendChild(divTextWrapper);
		messageBox.prepend(div);
	} else if (msg.chatBodyType == 'NEW') {
		let div = document.createElement('div');
		div.classList.add('chat-read-divider');
		div.textContent = msg.chatBodyContent;
		messageBox.prepend(div);	
	}
}
// 모달 및 사용자 추가
const addMemberBtn = document.querySelector('#addMemeber');
const modal = document.getElementById("addMemberModal");
const closeBtn = document.getElementById("closeModal");
const selectedMembers = document.getElementById("selectedMembers");
let memberList = document.querySelector('#memberList');
let staffs = [];
let staffNames = [];

addMemberBtn.addEventListener("click", () => {
	renderHtmlChatAddMemeber(chatRoomNum);
	modal.style.display = "flex";
});

closeBtn.addEventListener("click", () => {
	let targetAll = document.querySelectorAll('.selected-member[data-staffCode]');
	targetAll.forEach(t => {
		t.remove();
	});
	let targetAllMember = document.querySelectorAll('.member-item');
	targetAllMember.forEach(t => {
		t.remove();
	});
	modal.style.display = "none";
});

function renderHtmlChatAddMemeber(chatRoomNum) {
	fetch('/msg/room/add', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ chatRoomNum: chatRoomNum })
	})
	.then(response => response.json())
	.then(response => {
		response.forEach(el => {
			renderHtmlInner(el)
		});
	});
}

function renderHtmlInner(el) {
	let div = document.createElement('div');
	div.classList.add('member-item');
	let divIn = document.createElement('div')
	divIn.classList.add('member-info');
	let img = document.createElement('img');
	img.setAttribute('src', '/file/staff/' + el.staffAttachmentDTO.attachmentDTO.savedName);
	let divInIn = document.createElement('div');
	let divName = document.createElement('div');
	divName.classList.add('member-name');
	divName.innerText = el.staffName;
	let divRole = document.createElement('div');
	divRole.classList.add('member-role');
	divRole.innerText = el.jobDTO.jobDetail
	let inp = document.createElement('input');
	inp.setAttribute('type', 'checkbox');
	inp.setAttribute('value', el.staffCode);
	inp.addEventListener("change", function() {
	    if (this.checked) {
			staffs.push(this.value);
			staffNames.push(el.staffName);
	        let selDiv = document.createElement("div");
	        selDiv.classList.add("selected-member"); 
	        selDiv.setAttribute("data-staffCode", el.staffCode);

	        let selImg = document.createElement("img");
	        selImg.src = '/file/staff/' + el.staffAttachmentDTO.attachmentDTO.savedName;
	        selImg.classList.add("selected-img");

	        let selName = document.createElement("span");
	        selName.classList.add("selected-name");
	        selName.innerText = el.staffName;

	        selDiv.appendChild(selImg);
	        selDiv.appendChild(selName);
	        selectedMembers.appendChild(selDiv);
	    } else {
	        const target = selectedMembers.querySelector(`.selected-member[data-staffCode="${el.staffCode}"]`);
	        if (target) selectedMembers.removeChild(target);
			if (staffs.indexOf(this.value) > -1) staffs.splice(staffs.indexOf(this.value), 1);
			if (staffNames.indexOf(el.staffName) > -1) staffNames.splice(staffNames.indexOf(el.staffName), 1);
	    }
	});
	
	divInIn.appendChild(divName);
	divInIn.appendChild(divRole);
	divIn.appendChild(img);
	divIn.appendChild(divInIn);
	div.appendChild(divIn);
	div.appendChild(inp);
	memberList.appendChild(div);
}

const joinStaffBtn = document.querySelector('#addMembers');
const form = document.querySelector('#form');
joinStaffBtn.addEventListener('click', () => {
	const contents = staffNames.join("님, ") + '님이 초대되었습니다.';
	const message = {
	    chatBodyType: "NEW",
	    contents: contents,
	    chatRoomNum: chatRoomNum,
	    staffCode: sender,
		chatBodyContent: contents,
		staffName: senderName
	};
	stompClient.send("/pub/chat/" + chatRoomNum, {}, JSON.stringify(message));
	fetch('/msg/room/join', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({staffs: staffs, chatRoomNum: chatRoomNum})
	})
	.then(response => response.json())
	.then(response => {
		console.log(response);
		modal.style.display = "none";
	});
});