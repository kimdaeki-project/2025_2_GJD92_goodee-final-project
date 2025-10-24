function changePw() {
	const form = document.querySelector("#passwordUpdate")
	
	Swal.fire({
	  text: "비밀번호를 변경하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#3085d6",
	  cancelButtonColor: "#d33",
	  confirmButtonText: "변경",
		cancelButtonText: "취소"
	}).then((result) => {
	  if (result.isConfirmed) {
	    form.method = "POST"
			form.action = "/staff/password/update"
			form.submit()
	  }
	});
}