console.log('create_modal.js 연결')

let staff = [];
//const savedStaff = document.querySelectorAll()

// 모달의 회원 추가 버튼
const addBtn = document.getElementById('addBtn');
const saveBtn = document.getElementById('saveStaffBtn');
const deptBtn = document.querySelectorAll('.dept-btn');

document.addEventListener('DOMContentLoaded', function () {
	// 모달 창이 켜졌을때 실행
	document.addEventListener('shown.bs.modal', function(e) {
		if (e.target.id === 'shareModal') {
			
			// 사원 리스트 가져옴
			fetch('/drive/staffList')
			.then(r => r.json())
			.then(r => {
				// 전역 변수에 저장
				staff = r;
				staff.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode);
				renderStaff(staff);
			})
			.catch(error => console.log('fetch에러', error))
		}
	});
});


/*
	모달창에 체크된 사원들 오른쪽으로 이동
*/
addBtn.addEventListener('click', () => {
	const staffList = document.getElementById('staffList');
	const selectedList = document.getElementById('selectedList');
	const savedStaff = document.getElementById('savedStaff'); // tbody
	
	// 요소 확인용 안전장치
	if (!staffList || !selectedList || !savedStaff) {
		console.log('staffList or selectedList 요소없음')
		return;	
	}
	
	// 체크되어있는 요소들을 가져옴
	const checkedBoxes = staffList.querySelectorAll('input[type="checkbox"]:checked');

	// 이미 추가되어있는 사원	
	const mainStaffCode = Array.from(savedStaff.querySelectorAll('input[type="hidden"')).map(input => input.value);
	
	// 중복사원이 검사
	// 중복사원 존재시 선택된 체크박스 모두 해제 후 메서드 즉시 종료
	for (const check of checkedBoxes) {
		const staffCode = check.value;
		if (mainStaffCode.includes(staffCode)) {
			alert('이미 추가된 사용자 입니다')
			for(const chk of checkedBoxes) {
				chk.checked = false;				
			}
			return;
		}
	}
 	
	// 체크된 사원들을 오른쪽으로 이동시킴
	checkedBoxes.forEach((check) => {
		const li = check.closest('li'); // chk.closest('li') 상위(부모)방향으로 이동하며 가장 가까운 li태그를 반환
		const text = li.querySelector('span').textContent // 사원 정보 태그 가져옴
		const value = check.value; // StaffCode 가져옴 
		
		// 추가된 리스트에 등록할 li 생성
		const newLi = document.createElement('li');
		newLi.className = 'list-group-item d-flex justify-content-between align-items-center';
		
		// X버튼과 빼내온 사원 정보 생성
		newLi.innerHTML = `<span data-staff-code="${value}">${text}</span><button class="btn-close btn-close-white remove-btn" aria-label="Remove"></button>`;

		// 삭제 버튼 동작 추가		
		newLi.querySelector(`.remove-btn`).addEventListener('click', function () {
			newLi.remove();
		});
		
		// 추가된 리스트에 li 등록
		selectedList.appendChild(newLi);
		
		// 사원 리스트 체크박스 해제
		check.checked = false;
	})
})


/*
	메인 컨텐츠에 사원 추가
*/
saveBtn.addEventListener('click', () => {
	const selectedList = document.getElementById('selectedList');
	const savedStaff = document.getElementById('savedStaff'); // tbody의 id
	
	// 해당 태그가 있는지 확인하는 안전장치
	if(!selectedList || !savedStaff) {
		console.log('selectedList or savedStaff 태그 없음')
		return;
	}
	
	// span에 저장된 data-staff-code를 가져오기 위함
	const spans = selectedList.querySelectorAll('span')
	
	spans.forEach(span => {
		//console.log(el)
		const staffCode = span.getAttribute('data-staff-code');
		// 배열의 staffCode와 span태그의 data속성으로 가져온 staffCode가 일치하는 객체를 반환  
		const staffObj = staff.find(s => s.staffCode == staffCode)
		// 반환된값이 없다면 종료
		if(!staffObj) return;
		
		const tr = document.createElement('tr');
		tr.innerHTML = `
				<th scope="row">
				<button class="btn-close btn-close-white remove-saved" aria-label="Remove"></button>
				<input type="hidden" value="${staffObj.staffCode}">
				</th>
				<td><i class="material-symbols-rounded opacity-5 fs-5">contacts_product</i></td>
				<td>${staffObj.staffName}</td>
				<td>${staffObj.jobDTO.jobDetail}</td>
				<td>${staffObj.deptDTO.deptDetail}</td>`;
				
		tr.querySelector('.remove-saved').addEventListener('click', () => {
			tr.remove();
		})		
		
		savedStaff.appendChild(tr);
	})
	
	const modalEl = document.getElementById('shareModal');
	const modal = bootstrap.Modal.getInstance(modalEl);
	if(modal) modal.hide();
	selectedList.innerHTML = '';
	
})

/*
	부서 선택
*/
deptBtn.forEach(d => {
	d.addEventListener('click', function(e){
		const teamName = e.target.getAttribute('data-team');
		console.log(teamName);
		if(teamName == '전체') {
			renderStaff(staff);
			return;
		}
				
		const team = staff.filter(s => s.deptDTO.deptDetail == teamName);
		team.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode);
		renderStaff(team);
	})
})




/*
	모달창 사원 목록 랜더링 (매개변수 : 랜더링할 리스트)
*/
function renderStaff(list) {
	
	const ul = document.getElementById('staffList');
	// ul이 없다면 메서드 종료	
	if(!ul) return;
	
	// ul태그 내용 초기화
	ul.innerHTML = '';	
	list.forEach(staff => {
		// li 태그 생성
		const li = document.createElement('li');
		// 생성한 li 태그에 class 추가
		li.className = 'list-group-item d-flex align-items-center'
		
		// li태그 내부에 다른 태크들 추가
		li.innerHTML = `<input type="checkbox" class="me-2" value="${staff.staffCode}">
		      <span>${staff.staffName} (${staff.jobDTO.jobDetail}) ${staff.deptDTO.deptDetail}</span>`;
		// ul안에 생성한 li 태그 추가
		ul.appendChild(li);
	})
}















