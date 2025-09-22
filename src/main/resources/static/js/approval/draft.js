let staffs = []
let currentDept = null
let currentUser = null

const addBtn = document.getElementById('addBtn')
const selectedList = document.getElementById("selectedList")
const supportedList = document.getElementById("supportedList")
const saveBtn = document.getElementById('saveStaffBtn')
const deptBtn = document.querySelectorAll('.dept-btn')
const searchInput = document.getElementById('searchInput')
const shareStaffs = document.querySelectorAll('.shareStaff')

new Sortable(selectedList, {
	animation: 150,
	ghostClass: "drag-ghost",
	chosenClass: "drag-chosen",
	dragClass: "dragging"
});

document.addEventListener('DOMContentLoaded', function () {
	document.addEventListener('shown.bs.modal', function(e) {
		
		if (e.target.id === 'shareModal') {
			fetch('/approval/staff')
			.then(data => data.json())
			.then(data => {
				staffs = data
				staffs.sort((a, b) => a.jobDTO.jobCode - b.jobDTO.jobCode)
				currentUser = staffs.find(staff => staff.staffCode == loginStaffCode)
				staffs = staffs.filter(staff => staff.staffCode != loginStaffCode)
				currentDept = staffs
				renderStaff(staffs)
			})
			.catch(error => console.log('Fetch Error!', error))
		}
	})
	
	if(shareStaffs) {
		shareStaffs.forEach((tr) => {
			tr.querySelector('.remove-saved').addEventListener('click', () => {
					tr.remove()
			})
		})
	}
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
		
		selectedList.appendChild(newLi)
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

saveBtn.addEventListener('click', () => {
	const approverList = document.getElementById('approverList')
	const receiptList = document.getElementById('receiptList')
	const agreeList = document.getElementById('agreeList')
	const spans = selectedList.querySelectorAll('span')
	const supports = supportedList.querySelectorAll('span')
	
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
			<span> ${currentUser.deptDTO.deptDetail}</span>
		</div>
		<div class="d-flex justify-content-end align-items-center mt-1" style="width: 80%; margin: 0 auto;">
			<i class="material-symbols-rounded fs-5 me-1" style="color: black;">contacts_product</i>
			<span>${currentUser.jobDTO.jobDetail} ${currentUser.staffName}</span>
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

function sendApproval() {
	const form = document.querySelector("#approvalForm")
	
	Swal.fire({
	  text: "기안을 등록 하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "확인",
		cancelButtonText: "취소"
	}).then((result) => {
	  if (result.isConfirmed) {
	    form.submit();
	  }
	});
}