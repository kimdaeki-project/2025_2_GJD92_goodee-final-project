/**
 * connect.js - 그룹채팅일 때 추가로 로드할 모달 관련 파일
 */
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
// 채팅방 나가기
const leaveBtn = document.querySelector('#chat-leave');
leaveBtn.addEventListener('click', () => {
	Swal.fire({
		text: "그룹 채팅방을 나가시겠습니까?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#191919",
		confirmButtonText: "확인",
		cancelButtonText: "취소"
	})
	.then(result => {
		if (result.isConfirmed) {
			const contents = senderName + '님이 채팅방을 나갔습니다.';
			const message = {
			    chatBodyType: "NEW",
			    contents: contents,
			    chatRoomNum: chatRoomNum,
			    staffCode: sender,
				chatBodyContent: contents,
				staffName: senderName
			};
			stompClient.send("/pub/chat/" + chatRoomNum, {}, JSON.stringify(message));
			fetch('/msg/room/leave', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({chatRoomNum: chatRoomNum})
			})
			.then(response => response.json())
			.then(response => {
				if (response) {
					location.href = '/msg/room';
				}
			})
		}		
	})
});
// 멤버 목록 띄우기(그룹 채팅일 때)
const groupMembersBtn = document.querySelector('.group-member-list');
const modalForGroupMembers = document.querySelector('#viewGroupMembers');
const modalForGroupMembersInternal = document.querySelector('#viewGroupMembersInternal');
groupMembersBtn.addEventListener('click', () => {
	getGroupChatMembersAndRenderHtml();
	modalForGroupMembers.classList.add('active');
	modalForGroupMembersInternal.classList.add('active');
});
modalForGroupMembers.addEventListener("click", (e) => {
	if (e.target === modalForGroupMembers) {
		modalForGroupMembers.classList.remove('active');
		modalForGroupMembersInternal.classList.remove('active');
		let notMe = document.querySelectorAll('.not-me');
		notMe.forEach(s => {
			s.remove();
		});
	}
});
// 멤버 목록을 동적으로 가져오고 html을 빌드하는 스크립트
function getGroupChatMembersAndRenderHtml() {
	fetch('/msg/room/groupMembers/' + chatRoomNum)
	.then(response => response.json())
	.then(response => {
		let list = document.querySelector('.group-member-view-list');
		let count = document.querySelector('.group-member-count-span')
		count.innerText = response.length;
		response.forEach(el => {
			if (el.staffDTO.staffCode != sender) {				
				let div = document.createElement('div');
				div.classList.add('group-member-card', 'not-me');
				let img = document.createElement('img');
				img.setAttribute('src', '/file/staff/' + el.staffDTO.staffAttachmentDTO.attachmentDTO.savedName);
				img.classList.add('group-profile-img');
				let divIn = document.createElement('div');
				divIn.classList.add('group-member-info');
				let divName = document.createElement('div');
				divName.classList.add('group-member-name');
				divName.innerText = el.staffDTO.staffName;
				let divPos = document.createElement('div');
				divPos.classList.add('group-member-position');
				divPos.innerText = el.staffDTO.jobDTO.jobDetail;
				
				divIn.appendChild(divName);
				divIn.appendChild(divPos);
				div.appendChild(img);
				div.appendChild(divIn);
				list.appendChild(div);
			}
		});
	});
}