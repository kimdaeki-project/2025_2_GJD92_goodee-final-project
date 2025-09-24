function checkApproval(result) {
	const form = document.querySelector("#apvrContentForm")
	let apvrComment = document.querySelector("#apvrComment")
	if (apvrComment) {
		apvrComment = apvrComment.value
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