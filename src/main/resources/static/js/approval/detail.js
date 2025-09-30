let staffs = []
let currentDept = null
let draftUser = null

const addBtn = document.getElementById('addBtn')
const receiptBtn = document.getElementById('receiptBtn')
const agreeBtn = document.getElementById('agreeBtn')
const selectedList = document.getElementById("selectedList")
const supportedList = document.getElementById("supportedList")
const saveBtn = document.getElementById('saveStaffBtn')
const deptBtn = document.querySelectorAll('.dept-btn')
const searchInput = document.getElementById('searchInput')

const receiveList = document.getElementById("receiveList")
const receiveBtn = document.getElementById('receiveBtn')
const saveReceiveBtn = document.getElementById('saveReceiveBtn')
const deptBtnReceive = document.querySelectorAll('.dept-btn-receive')
const receiveSearchInput = document.getElementById('receiveSearchInput')

new Sortable(selectedList, {
	animation: 150,
	ghostClass: "drag-ghost",
	chosenClass: "drag-chosen",
	dragClass: "dragging",
	filter: '.filtered',
	onMove: function (event) {
		const related = event.related
		    
    if (related.classList.contains("filtered")) {
      return false
    }
    
    return true
	}
});

document.addEventListener('DOMContentLoaded', function () {
	document.addEventListener('shown.bs.modal', function(e) {
		
		const postApprover = document.querySelectorAll(".post-apvr")
		const postReceiver = document.querySelectorAll(".post-recp")
		const postAgreer = document.querySelectorAll(".post-agre")
		
		if (e.target.id === 'receiveModal') {
			fetch('/approval/staff')
			.then(data => data.json())
			.then(data => {
				staffs = data
				staffs.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode)
				currentUser = staffs.find(staff => staff.staffCode == loginStaffCode)
				staffs = staffs.filter(staff => staff.staffCode != loginStaffCode)
				currentDept = staffs
				renderReceiveStaff(staffs)
				
				postReceiver.forEach((receiver) => {
					const receiverStaff = staffs.find((staff) => staff.staffCode == receiver.value)
					
					const value = receiverStaff.staffCode
					const text = `${receiverStaff.staffName}(${receiverStaff.jobDTO.jobDetail}) ${receiverStaff.deptDTO.deptDetail}`
					
					const newLi = document.createElement('li')
					newLi.className = 'list-group-item d-flex justify-content-between align-items-center bg-gradient-dark text-white'
					newLi.innerHTML = `<span data-staff-code="${value}">${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

					newLi.querySelector(`.remove-btn`).addEventListener('click', () => {
						Swal.fire({ text: "기존 수신자는 삭제할 수 없습니다.", icon: "warning" })
					})
					
					receiveList.appendChild(newLi)
				})
			})
			.catch(error => console.log('Fetch Error!', error))
		}
		
		if (e.target.id === 'shareModal') {
			fetch('/approval/staff')
			.then(data => data.json())
			.then(data => {
				staffs = data
				staffs.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode)
				draftUser = staffs.find(staff => staff.staffCode == draftStaffCode)
				staffs = staffs.filter(staff => staff.staffCode != draftStaffCode)
				currentDept = staffs
				renderStaff(staffs)
				
				postApprover.forEach((approver) => {
					const approverStaff = staffs.find((staff) => staff.staffCode == approver.value)
					const approved = approver.getAttribute("data-approved")
					
					const value = approverStaff.staffCode
					const text = `${approverStaff.staffName}(${approverStaff.jobDTO.jobDetail}) ${approverStaff.deptDTO.deptDetail}`
					
					const newLi = document.createElement('li')
					
					if (approved == 'Y') {
						newLi.className = 'list-group-item d-flex justify-content-between align-items-center bg-gradient-dark text-white filtered'					
					} else {
						newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
					}
					newLi.innerHTML = `<span data-staff-code="${value}" data-approved="${approved}">${text}</span><button class="btn-close btn-close-white remove-btn"></button>`
					
					newLi.addEventListener("click", () => {
						if (newLi.classList.contains("filtered")) {
							Swal.fire({ text: "이미 결재한 사원의 순서는 변경할 수 없습니다.", icon: "warning"})
						}
					})

					newLi.querySelector(`.remove-btn`).addEventListener('click', (event) => {
						const isApproved = event.target.parentElement.querySelector("span").getAttribute("data-approved")
						
						if (isApproved == 'Y') {
							Swal.fire({ text: "이미 결재한 사원은 제외할 수 없습니다.", icon: "warning"})
						} else {
							newLi.remove()
						}
					})
					
					selectedList.appendChild(newLi)
				})
				
				postReceiver.forEach((receiver) => {
					const receiverStaff = staffs.find((staff) => staff.staffCode == receiver.value)
					
					const value = receiverStaff.staffCode
					const text = `${receiverStaff.staffName}(${receiverStaff.jobDTO.jobDetail}) ${receiverStaff.deptDTO.deptDetail}`
					
					const newLi = document.createElement('li')
					newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
					newLi.innerHTML = `<span data-staff-code="${value}" data-staff-type="receipt">[수] ${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

					newLi.querySelector(`.remove-btn`).addEventListener('click', () => {
						newLi.remove()
					})
					
					supportedList.appendChild(newLi)
				})
				
				postAgreer.forEach((agreer) => {
					const agreerStaff = staffs.find((staff) => staff.staffCode == agreer.value)
					const approved = agreer.getAttribute("data-approved")
					
					const value = agreerStaff.staffCode
					const text = `${agreerStaff.staffName}(${agreerStaff.jobDTO.jobDetail}) ${agreerStaff.deptDTO.deptDetail}`
					
					const newLi = document.createElement('li')
					
					if (approved == 'Y') {
						newLi.className = 'list-group-item d-flex justify-content-between align-items-center bg-gradient-dark text-white'					
					} else {
						newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
					}
					newLi.innerHTML = `<span data-staff-code="${value}" data-staff-type="agree" data-approved="${approved}">[합] ${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

					newLi.querySelector(`.remove-btn`).addEventListener('click', (event) => {
						const isApproved = event.target.parentElement.querySelector("span").getAttribute("data-approved")
						
						if (isApproved == 'Y') {
							Swal.fire({ text: "이미 결재한 사원은 제외할 수 없습니다.", icon: "warning"})
						} else {
							newLi.remove()
						}
					})
					
					supportedList.appendChild(newLi)
				})
				
				
			})
			.catch(error => console.log('Fetch Error!', error))
		}
	})
})

addBtn.addEventListener('click', () => {
	const staffList = document.getElementById('staffList')
	
	const checkedInput = staffList.querySelectorAll('input[type="checkbox"]:checked')
 	
	checkedInput.forEach((check) => {
		const li = check.closest('li')
		const text = li.querySelector('span').textContent
		const value = check.value
		
		const newLi = document.createElement('li')
		newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
		newLi.innerHTML = `<span data-staff-code="${value}">${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

		newLi.querySelector(`.remove-btn`).addEventListener('click', function () {
			newLi.remove()
		})
		
		selectedList.insertBefore(newLi, selectedList.firstChild)
		check.checked = false
	})
})

receiptBtn.addEventListener('click', () => {
	const staffList = document.getElementById('staffList')
	
	const checkedInput = staffList.querySelectorAll('input[type="checkbox"]:checked')
 	
	checkedInput.forEach((check) => {
		const li = check.closest('li')
		const text = li.querySelector('span').textContent
		const value = check.value
		
		const newLi = document.createElement('li')
		newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
		newLi.innerHTML = `<span data-staff-code="${value}" data-staff-type="receipt">[수] ${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

		newLi.querySelector(`.remove-btn`).addEventListener('click', function () {
			newLi.remove()
		})
		
		supportedList.appendChild(newLi)
		check.checked = false
	})
})

agreeBtn.addEventListener('click', () => {
	const staffList = document.getElementById('staffList')
	
	const checkedInput = staffList.querySelectorAll('input[type="checkbox"]:checked')
 	
	checkedInput.forEach((check) => {
		const li = check.closest('li')
		const text = li.querySelector('span').textContent
		const value = check.value
		
		const newLi = document.createElement('li')
		newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
		newLi.innerHTML = `<span data-staff-code="${value}" data-staff-type="agree">[합] ${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

		newLi.querySelector(`.remove-btn`).addEventListener('click', function () {
			newLi.remove()
		})
		
		supportedList.appendChild(newLi)
		check.checked = false
	})
})

receiveBtn.addEventListener('click', () => {
	const receiveStaffList = document.getElementById('receiveStaffList')
	
	const checkedInput = receiveStaffList.querySelectorAll('input[type="checkbox"]:checked')
 	
	checkedInput.forEach((check) => {
		const li = check.closest('li')
		const text = li.querySelector('span').textContent
		const value = check.value
		
		const newLi = document.createElement('li')
		newLi.className = 'list-group-item d-flex justify-content-between align-items-center'
		newLi.innerHTML = `<span data-staff-code="${value}">${text}</span><button class="btn-close btn-close-white remove-btn"></button>`

		newLi.querySelector(`.remove-btn`).addEventListener('click', function () {
			newLi.remove()
		})
		
		receiveList.appendChild(newLi)
		check.checked = false
	})
})

saveBtn.addEventListener('click', () => {
	const approverList = document.getElementById('approverList')
	const receiptList = document.getElementById('receiptList')
	const agreeList = document.getElementById('agreeList')
	const spans = selectedList.querySelectorAll('span')
	const supports = supportedList.querySelectorAll('span')
	
	Swal.fire({
	  text: "결재선을 재지정 하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "확인",
		cancelButtonText: "취소"
	}).then((result) => {
	  if (result.isConfirmed) {
			approverList.innerHTML = ""
			receiptList.innerHTML = ""
			agreeList.innerHTML = ""
			
			spans.forEach((span, index) => {
				const staffCode = span.getAttribute('data-staff-code')
				let confirm = "검토"
				if (index == 0) confirm = "승인"
				
				const staff = staffs.find(s => s.staffCode == staffCode)
				if(!staff) return
				
				const liTag = document.createElement('li');
				liTag.innerHTML = `
					<input type="hidden" name="approver" value="${staff.staffCode}">
					<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
						<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">${confirm}</div>
						<span> ${staff.deptDTO.deptDetail}</span>
					</div>
					<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
						<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
						<span>${staff.jobDTO.jobDetail} ${staff.staffName}</span>
					</div>
					<div class="text-center mt-2" style="color: black;">│</div>
					`
						
				approverList.appendChild(liTag)
			})
			
			const lastTag = document.createElement('li');
			lastTag.innerHTML = `
				<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
					<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">기안</div>
					<span> ${draftUser.deptDTO.deptDetail}</span>
				</div>
				<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
					<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
					<span>${draftUser.jobDTO.jobDetail} ${draftUser.staffName}</span>
				</div>
				`
				
			approverList.appendChild(lastTag)
			
			supports.forEach((span) => {
				const staffCode = span.getAttribute('data-staff-code')
				const staffType = span.getAttribute('data-staff-type')
				
				const staff = staffs.find(s => s.staffCode == staffCode)
				if(!staff) return
				
				if (staffType == 'receipt') {
					const liTag = document.createElement('li');
					liTag.innerHTML = `
						<input type="hidden" name="receiver" value="${staff.staffCode}">
						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
							<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">수신</div>
							<span> ${staff.deptDTO.deptDetail}</span>
						</div>
						<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
							<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
							<span>${staff.jobDTO.jobDetail} ${staff.staffName}</span>
						</div>
						`
							
					receiptList.appendChild(liTag)
				}
				
				if (staffType == 'agree') {
					const liTag = document.createElement('li');
					liTag.innerHTML = `
						<input type="hidden" name="agreer" value="${staff.staffCode}">
						<div class="d-flex justify-content-between align-items-center mt-2" style="width: 80%; margin: 0 auto;">
							<div class="rounded m-0 px-1 py-0" style="border: 1px solid black; color: black; font-size: 14px;">합의</div>
							<span> ${staff.deptDTO.deptDetail}</span>
						</div>
						<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
							<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
							<span>${staff.jobDTO.jobDetail} ${staff.staffName}</span>
						</div>
						`
							
					agreeList.appendChild(liTag)
				}
				
			})
			
			const modalEl = document.getElementById('shareModal')
			const modal = bootstrap.Modal.getInstance(modalEl)
			
			if(modal) modal.hide()
			selectedList.innerHTML = ''
			
			const changeLineForm = document.querySelector("#changeApprovalLineForm")
			changeLineForm.submit()
	  }
	})

})

saveReceiveBtn.addEventListener('click', () => {
	const receives = receiveList.querySelectorAll('span')
	
	Swal.fire({
	  text: "수신자를 추가 하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "확인",
		cancelButtonText: "취소"
	}).then((result) => {
	  if (result.isConfirmed) {
			const formData = new FormData()
			
			receives.forEach((receive) => {
				const staffCode = receive.getAttribute('data-staff-code')
				formData.append("receiver", staffCode)
			})
			
			fetch(location.pathname + "/receive", {
				method : "POST",
				body : formData
			})
			.then((data) => data.text())
			.then((data) => {
				if (data == "true") {
					Swal.fire({ text: "수신자가 추가되었습니다.", icon: "success"})
					.then((result) => {
						location.href = location.pathname
					})
				} else {
					Swal.fire({ text: "수신자 추가 중 오류가 발생했습니다.", icon: "warning"})
				}
			})
			.catch((e) => console.log(e))
		}
	})
})

deptBtn.forEach(d => {
	d.addEventListener('click', function(e){
		
		searchInput.value = ''
		const deptName = e.target.getAttribute('data-team')
		
		if(deptName == '전체') {
			currentDept = staffs 
			renderStaff(staffs)
			return
		}
				
		const DeptStaff = staffs.filter(s => s.deptDTO.deptDetail == deptName)
		currentDept = DeptStaff.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode)
		renderStaff(currentDept)
	})
})

deptBtnReceive.forEach(d => {
	d.addEventListener('click', function(e){
		
		receiveSearchInput.value = ''
		const deptName = e.target.getAttribute('data-team')
		
		if(deptName == '전체') {
			currentDept = staffs
			renderReceiveStaff(staffs)
			return
		}
				
		const DeptStaff = staffs.filter(s => s.deptDTO.deptDetail == deptName)
		currentDept = DeptStaff.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode)
		renderReceiveStaff(currentDept)
	})
})

receiveSearchInput.addEventListener('input', (e) => {
	const keyword = e.target.value;
	
	const filteredStaff = currentDept.filter(s => 
		s.staffName.includes(keyword) ||
		s.jobDTO.jobDetail.includes(keyword)
	);
	renderReceiveStaff(filteredStaff);
})

searchInput.addEventListener('input', (e) => {
	const keyword = e.target.value;
	
	const filteredStaff = currentDept.filter(s => 
		s.staffName.includes(keyword) ||
		s.jobDTO.jobDetail.includes(keyword)
	);
	renderStaff(filteredStaff);
})

function renderStaff(list) {
	const staffList = document.getElementById('staffList');
	if(!staffList) return;
	
	staffList.innerHTML = ''
	list.forEach(staff => {
		const li = document.createElement('li')
		li.className = 'list-group-item d-flex align-items-center'
		li.innerHTML = `<input type="checkbox" class="me-2" value="${staff.staffCode}"><span>${staff.staffName}(${staff.jobDTO.jobDetail}) ${staff.deptDTO.deptDetail}</span>`
		
		staffList.appendChild(li);
	})
}

function renderReceiveStaff(list) {
	const receiveStaffList = document.getElementById('receiveStaffList');
	if(!receiveStaffList) return;
	
	receiveStaffList.innerHTML = ''
	list.forEach(staff => {
		const li = document.createElement('li')
		li.className = 'list-group-item d-flex align-items-center'
		li.innerHTML = `<input type="checkbox" class="me-2" value="${staff.staffCode}"><span>${staff.staffName}(${staff.jobDTO.jobDetail}) ${staff.deptDTO.deptDetail}</span>`
		
		receiveStaffList.appendChild(li);
	})
}



function checkApproval(result) {
	const form = document.querySelector("#apvrContentForm")
	let apvrComment = document.querySelector("#apvrComment")
	if (apvrComment) {
		apvrComment = apvrComment.value
	}
	
	if (!hasSign) {
		Swal.fire({ text: "먼저 서명을 등록해주세요.", icon: "warning" })
		return
	}
	
	if (result) {
		Swal.fire({
		  text: "결재하시겠습니까?",
		  icon: "question",
		  showCancelButton: true,
		  confirmButtonColor: "#3085d6",
		  cancelButtonColor: "#d33",
		  confirmButtonText: "확인",
			cancelButtonText: "취소"
		}).then((result) => {
		  if (result.isConfirmed) {
				if (apvrComment == null || apvrComment == "") {
					document.querySelector("#apvrComment").value = "없음"
				}
				form.querySelector("input[name='apvrResult']").value = "true"
				
		    form.submit()
		  }
		});
	}
	
	if (!result) {
		Swal.fire({
		  text: "반려하시겠습니까?",
		  icon: "question",
		  showCancelButton: true,
		  confirmButtonColor: "#3085d6",
		  cancelButtonColor: "#d33",
		  confirmButtonText: "확인",
			cancelButtonText: "취소"
		}).then((result) => {
		  if (result.isConfirmed) {
		    if (apvrComment == null || apvrComment == "") {
					Swal.fire({ text: "결재 의견을 입력해주세요.", icon: "warning" })
				} else {
					form.querySelector("input[name='apvrResult']").value = "false"
					
					form.submit()
				}
		  }
		});
	}
}

function openApprovalSign() {
	window.open("/approval/sign", "_blank", "width=300, height=400, top=250, left=400, menubar=no, toolbar=no, location=no, status=no")
}