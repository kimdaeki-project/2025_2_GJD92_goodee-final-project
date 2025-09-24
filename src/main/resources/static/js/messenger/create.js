/**
 * create.jsp 랑 연동
 * 채팅방 생성하는 스크립트 코드
 */
const addBtns = document.querySelectorAll('.add-user');
const addedUsers = document.querySelector('#addedUsers');
let room = [];
addBtns.forEach(el => {
	el.addEventListener('click', () => {				
		const nameDiv = document.createElement('div');
		
		const nameSpan = document.createElement('span');
		nameSpan.innerText = el.value;
		
		const axe = document.createElement('button');
		axe.setAttribute('type', 'button');
		axe.innerText = 'X';
		
		nameDiv.appendChild(nameSpan);
		nameDiv.appendChild(axe);
		
		addedUsers.appendChild(nameDiv);
		room.push(el.value);
	});
});

const createRoomBtn = document.querySelector('#createRoom');
const form = document.querySelector('#form');
createRoomBtn.addEventListener('click', () => {
	if (room.length > 0) {
		const logged = document.querySelector('#logged');
		room.push(logged.value);
		let addedStaff = document.querySelector('#addedStaff');
		addedStaff.value = room.join(",");
		form.submit();
	}
});