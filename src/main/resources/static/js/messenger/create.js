/**
 * create.jsp 랑 연동
 * 채팅방 생성하는 스크립트 코드
 */
const addedUsers = document.querySelector('#addedUsers');
const checkBoxes = document.querySelectorAll('input[type="checkbox"]');
const clearAll = document.querySelector('.clear-all');
let list = document.querySelector('.selected-list');
let number = document.querySelector('.selected-number');
let room = [];
checkBoxes.forEach(m => {
	m.addEventListener('change', function () {
		if (this.checked) {
			renderHtmlMemberElement(this);
			number.innerText = parseInt(number.innerText) + 1;
			room.push(this.value);
		} else {
			let target = document.querySelector(`.selected-item[data-staffCode="${ this.value }"]`);
			if (target) target.remove();
			number.innerText = parseInt(number.innerText) - 1;
			if (room.indexOf(this.value) > -1) {
				room.splice(room.indexOf(this.value), 1);
			}
		}
	});
})

function renderHtmlMemberElement(check) {
	let div = document.createElement('div');
	div.classList.add('selected-item');
	div.setAttribute("data-staffCode", check.value);
	let inDiv = document.createElement('div');
	inDiv.classList.add('selected-info');
	let img = document.createElement('img');
	img.setAttribute('src', check.getAttribute('data-img'));
	let nameDiv = document.createElement('div');
	nameDiv.classList.add('member-name');
	nameDiv.innerText = check.getAttribute('data-name');
	let span = document.createElement('span');
	span.classList.add('material-icons', 'remove-btn');
	
	inDiv.appendChild(img);
	inDiv.appendChild(nameDiv);
	div.appendChild(inDiv);
	div.appendChild(span);
	list.appendChild(div);
}

clearAll.addEventListener('click', () => {
	room.length = 0;
	let targetAll = document.querySelectorAll('.selected-item[data-staffCode]');
	targetAll.forEach(t => {
		t.remove();
	});
	number.innerText = 0;
	checkBoxes.forEach(m => {
		m.checked = false;
	});
});

const createRoomBtn = document.querySelector('#createRoom');
const form = document.querySelector('#form');
createRoomBtn.addEventListener('click', () => {
	if (room.length > 1) {
		let chatRoomNameInput = document.querySelector('#chatRoomName').value;
		if (chatRoomNameInput == null || chatRoomNameInput == '') {
			Swal.fire({
				text: "채팅방 이름을 입력해주세요.",
				icon: "warning",
				confirmButtonColor: "#191919",
				confirmButtonText: "확인"
			});
			return;
		}
		const logged = document.querySelector('#logged');
		room.push(logged.value);
		let addedStaff = document.querySelector('#addedStaff');
		addedStaff.value = room.join(",");
		form.submit();
	} else {
		Swal.fire({
			text: "최소 2명 이상의 인원을 선택해주세요.",
			icon: "warning",
			confirmButtonColor: "#191919",
			confirmButtonText: "확인"
		});
		return;
	}
});