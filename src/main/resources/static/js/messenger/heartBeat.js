/**
 * connection
 * const staffCode = '<sec:authentication property="principal.username"/>';
 */
let stompClient = null;
let currentChatRoomChecker = null;
const dm = document.querySelector('.dropdown-menu');
const alertBadge = document.querySelector('.badge.bg-danger.rounded-pill');

function connectWebSocket(staffCode) {
	const socket = new SockJS('http://localhost/ws-stomp');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log("Connected: " + frame);
		stompClient.subscribe("/sub/notify/" + staffCode, (msg) => {
			let payload = JSON.parse(msg.body);
			if (payload.type == 'APPROVAL') {
				showNotificationApproval(payload.msg, msg.headers.destination);				
			} else if (payload.type == 'CHATCOUNT') {
				plusOneChatCount(payload.msg);
			} else if (payload.type == 'SYNCCHATCOUNT') {
				synchronize(payload.msg);
			} else if (payload.type == 'NOUNREADCOUNT') {
				setChatRoomChecker(payload.msg);
			} else if (payload.type == 'GROUPINVITATION') {
				showNotificationApproval(payload.msg, msg.headers.destination);
			}
		})
	}, (err) => {
		console.error("Disconnected. Reconnecting in 5s...");
		setTimeout(() => connectWebSocket(staffCode), 5000);
	});
}

function showNotificationApproval(msg, destination) {
    const notyf = new Notyf();
    notyf.success('새로운 알림이 도착했습니다!');
	fetch('/alert/new', {
		method: 'post',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ staffCodeToDb: destination.split("/").pop() })    
	})
	.then(response => response.json())
	.then(response => {
		htmlTagBuilder(msg, response.alertNum);		
	});
	
	let alertCount = parseInt(alertBadge.innerText) + 1;
	alertCountDecider(alertCount);
}

function renderAlerts(staffCodeForAlert) {
	fetch('/alert/list', {
		method: 'post',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ staffCodeToDb: staffCodeForAlert })
	})
	.then(response => response.json())
	.then(response => {
		let alertCountNoDelete = 0;
		response.forEach(alert => {
			if (!alert.alertDelete) {
				htmlTagBuilder(alert.alertMsg, alert.alertNum);
				alertCountNoDelete++;				
			}
		});
		alertCountDecider(alertCountNoDelete);
	});
}

function htmlTagBuilder(msg, alertNum) {
	let type;
	if (msg.includes('결재')) type = 'aprv';
	else if (msg.includes('채팅방')) type = 'inv';
	let list = document.createElement('li');
	let listClassForDelete = 'list-' + alertNum;
	list.classList.add('mb-2', listClassForDelete);
	let anchor = document.createElement('a');
	anchor.classList.add('dropdown-item', 'border-radius-md');
	if (type == 'aprv') anchor.setAttribute('href', '/approval/' + msg.split(',')[1]);
	else if (type == 'inv') anchor.setAttribute('href', '#');
	let div = document.createElement('div');
	div.classList.add('d-flex', 'py-1', 'justify-content-between', 'align-items-center');
	let divLeft = document.createElement('div');
	divLeft.classList.add('d-flex');
	let close = document.createElement('button');
	close.classList.add('btn-close');
	close.setAttribute('aria-label', 'Close');
	close.setAttribute('value', alertNum);
	let divTop = document.createElement('div');
	divTop.classList.add('.my-auto');
	let img = document.createElement('img');
	img.classList.add('avatar', 'avatar-sm', 'me-3');
	if (type == 'aprv') img.setAttribute('src', '/images/heartBeat/aprv.png');
	else if (type == 'inv') img.setAttribute('src', '/images/heartBeat/invitation.png');
	let divBot = document.createElement('div');
	divBot.classList.add('d-flex', 'flex-column', 'justify-content-center');
	let h6 = document.createElement('h6');
	h6.classList.add('text-sm', 'font-weight-normal', 'mb-1');
	let span = document.createElement('span');
	span.classList.add('font-weight-bold');
	span.innerText = msg.split(',')[0];

	h6.appendChild(span);
	divBot.appendChild(h6);
	divTop.appendChild(img);
	divLeft.appendChild(divTop);
	divLeft.appendChild(divBot);
	div.appendChild(divLeft);
	div.appendChild(close)
	anchor.appendChild(div);
	list.appendChild(anchor);
	dm.prepend(list);
	// no-alert 제거
	removeNoAlert();
	
	close.addEventListener('click', function(e) {
		e.preventDefault(); e.stopPropagation();
		fetch('/alert/delete', {
			method: 'post',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ alertNumToDelete: this.value })
		})
		.then(response => response.json())
		.then(response => {
			if (response) {
				let toDeleteEl = document.querySelector('.list-' + alertNum);
				dm.removeChild(toDeleteEl);
				let alertCountFromDelete = parseInt(alertBadge.innerText) - 1;
				alertCountDecider(alertCountFromDelete);
				
				// no-alert 처리
				if (dm.querySelectorAll('li:not(.no-alert)').length === 0) noAlert();
			}
		});
	});
	anchor.addEventListener('click', function(e) {
		e.preventDefault(); e.stopPropagation();
		let alertNumForAnchor = this.querySelector('button').value; 
		fetch('/alert/delete', {
			method: 'post',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ alertNumToDelete: alertNumForAnchor})
		})
		.then(response => response.json())
		.then(response => {
			if (response) {
				let toDeleteEl = document.querySelector('.list-' + alertNum);
				dm.removeChild(toDeleteEl);
				let alertCountFromDelete = parseInt(alertBadge.innerText) - 1;
				alertCountDecider(alertCountFromDelete);
				
				// no-alert 처리
				if (dm.querySelectorAll('li:not(.no-alert)').length === 0) noAlert();
				
				window.location.href = this.href;
			}
		})
	});
}

function alertCountDecider(count) {
	if (count > 9) {			
		alertBadge.innerText = '9+';
	} else {
		alertBadge.innerText = count;
	}
}

function plusOneChatCount(chatRoomNum) {
	let bdg = document.querySelector('.badge-footer-display');
	let count = parseInt(bdg.innerText);
	if (currentChatRoomChecker == null) {
		count = count + 1;
	} else {
		if (currentChatRoomChecker != chatRoomNum) {
			count = count + 1;
		}
	}
	if (count > 9) {
		bdg.innerText = '9+';
	} else {
		bdg.innerText = count;
	}
}

function synchronize(chatRoomNum) {
	let data = [chatRoomNum];
	fetch("/msg/unread/count", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
	.then(res => {
		let bdg = document.querySelector('.badge-footer-display');
		let count = parseInt(bdg.innerText) - res.unread[chatRoomNum];
		if (count > 9) {
			bdg.innerText = '9+';
		} else {
			bdg.innerText = count;
		}
	});
}

function setChatRoomChecker(msg) {
	if (msg == 'DEPLETE') {
		currentChatRoomChecker = null;		
	} else {
		currentChatRoomChecker = msg;
	}
}
// 알림 없을 때 대비
function noAlert() {
	let noAlertList = document.querySelector('.no-alert');
	if (!noAlertList) {
		let li = document.createElement('li');
		li.classList.add('no-alert');
		li.innerText = '새 알림이 없습니다.';
		dm.appendChild(li);
	}
}
// 알림이 생겼을 때 no-alert 제거
function removeNoAlert() {
	let noAlertList = document.querySelector('.no-alert');
	if (noAlertList) noAlertList.remove();
}
// no-alert 초기화
document.addEventListener('DOMContentLoaded', () => {
	if (dm.querySelectorAll('li').length === 0) noAlert();
})