function movePage(page) {
	if (page == null) page = 0
	const search = document.querySelector("#searchText").value
	
	if (search != null && search != "") {
		location.href = `/staff/leave?page=${page}&search=${search}`		
	} else {
		location.href = `/staff/leave?page=${page}`
	}
}

function setStaffCode(staffCode, remainLeave, usedLeave) {
	document.querySelector("#staffCode").value = staffCode
	document.querySelector("#staffRemainLeave").value = remainLeave
	document.querySelector("#staffUsedLeave").value = usedLeave
}

function updateLeave() {
	const form = document.querySelector("#updateLeaveForm")
	
	Swal.fire({
	  text: "연차 정보를 수정하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "확인",
		cancelButtonText: "취소"
	}).then((result) => {
	  if (result.isConfirmed) {
	    form.submit()
	  }
	});
}