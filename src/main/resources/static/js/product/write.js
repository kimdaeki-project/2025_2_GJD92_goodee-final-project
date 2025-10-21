const ALLOWED_TYPES = ["image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp"];

document.querySelector("#attach").addEventListener("change", (event) => {
	
	const file = event.target.files[0];
	
	if (file) {
		// 이미지 타입 검사
	    if (!ALLOWED_TYPES.includes(file.type)) {
	      Swal.fire({
	        text: "이미지 파일(jpg, jpeg, png, gif, bmp, webp)만 업로드 가능합니다.",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
	      });
	      event.target.value = "";
	      document.querySelector("#preview").src = "";
	      return;
	    }
		
		if (event.target.files && event.target.files[0]) {
			const reader = new FileReader();
			
			reader.onload = function (event) {
				document.querySelector("#preview").src = event.target.result;
			};
			
			reader.readAsDataURL(event.target.files[0]);
			document.getElementById("fileMsg").innerHTML = "";
		} else {
			document.querySelector("#preview").src = "";
		}
	}
})