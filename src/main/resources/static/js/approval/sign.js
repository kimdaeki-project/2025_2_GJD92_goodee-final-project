document.querySelector("#attach").addEventListener("change", (event) => {
	if (event.target.files && event.target.files[0]) {
			const reader = new FileReader();
			
			reader.onload = function (event) {
				document.querySelector("#preview").src = event.target.result;
			};
			
			reader.readAsDataURL(event.target.files[0]);
		} else {
			document.querySelector("#preview").src = "";
		}
})

function changeSign() {
	const form = document.querySelector("#signForm")
	const file = document.querySelector("#attach")
	
	if (file.files.length == 0) {
		Swal.fire({ text: "서명을 등록해주세요.", icon: "warning" })
		return
	} else {
		Swal.fire({
		  text: "서명을 등록 하시겠습니까?",
		  icon: "question",
		  showCancelButton: true,
		  confirmButtonColor: "#3085d6",
		  cancelButtonColor: "#d33",
		  confirmButtonText: "확인",
			cancelButtonText: "취소"
		}).then((result) => {
		  if (result.isConfirmed) {
		    const formData = new FormData(form)
				
				fetch("/approval/sign", {
					method : "POST",
					body : formData
				})
				.then((data) => data.text())
				.then((data) => {
					if (data == "true") {
						Swal.fire({
						  text: "서명이 등록되었습니다.",
						  icon: "success",
						  showCancelButton: false,
						  confirmButtonColor: "#3085d6",
						  confirmButtonText: "확인"
						})
						.then((result) => {
						  if (result.isConfirmed) {
								window.close()
							}
						})
						.catch((e) => {
							Swal.fire({ text: "서명을 등록 중 오류가 발생했습니다.", icon: "warning" })
						})
					}
				})
		  }
		});
	}
}