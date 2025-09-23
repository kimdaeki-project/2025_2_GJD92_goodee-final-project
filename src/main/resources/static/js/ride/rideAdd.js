/**
 * 
 */
// 파일 미리보기
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


document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  const rideCodeInput = document.querySelector("input[name='rideCode']");
  const mode = form.getAttribute("data-mode"); 

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    // 1. 입력값 확인
    const requiredInputs = form.querySelectorAll("input, select");
    let emptyField = false;

    requiredInputs.forEach((el) => {
      const type = el.getAttribute("type");

      if (type === "file") {
				const existingFile = document.querySelector("#hasExistingFile");
				const hasExistingFile = existingFile && existingFile.value === "true";
        const isFileEmpty = !el.value.trim();

        if (mode === "add" && isFileEmpty) {
          // 등록 시: 파일 필수
          emptyField = true;
        }

        if (mode === "edit") {
          // 수정 시: 기존 파일 없고, 새 파일도 업로드 안 하면 오류
          if (!hasExistingFile && isFileEmpty) {
            emptyField = true;
          }
        }
      } else {
        if (!el.value.trim()) {
          emptyField = true;
        }
      }
    });

    if (emptyField) {
      Swal.fire({
        text: "입력하지 않은 정보가 있습니다.",
        icon: "warning",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "확인"
      });
      return;
    }

    // 2. 등록(add)일 때만 코드 중복 검사
    if (mode === "add") {
      const rideCode = rideCodeInput.value.trim();
			
      try {
				let isDuplicate = null;
				
				fetch("/ride/checkCode?rideCode=" + rideCode)
				.then((data) => data.json())
				.then((data) => {
					isDuplicate = data;
					
	        if (isDuplicate) {
	          Swal.fire({
	            text: "이미 있는 어트랙션 코드 입니다.",
	            icon: "error",
	            confirmButtonColor: "#3085d6",
	            confirmButtonText: "확인"
	          });
						
	          return;
	        } else {
						// 3. 최종 확인
				    Swal.fire({
				      text: "어트랙션을 등록하시겠습니까?",
				      icon: "question",
				      showCancelButton: true,
				      confirmButtonColor: "#3085d6",
				      cancelButtonColor: "#d33",
				      confirmButtonText: "등록",
				      cancelButtonText: "취소"
				    }).then((result) => {
				      if (result.isConfirmed) {
				        form.submit();
				      }
				    });
					}
				})
				
      } catch (err) {
        Swal.fire({
          text: "중복 검사 중 오류가 발생했습니다.",
          icon: "error",
          confirmButtonColor: "#3085d6",
          confirmButtonText: "확인"
        });
				
        return;
      }
    } else if (mode === "edit") {
			Swal.fire({
	      text: "어트랙션을 수정하시겠습니까?",
	      icon: "question",
	      showCancelButton: true,
	      confirmButtonColor: "#3085d6",
	      cancelButtonColor: "#d33",
	      confirmButtonText: "수정",
	      cancelButtonText: "취소"
	    }).then((result) => {
	      if (result.isConfirmed) {
	        form.submit();
	      }
	    });
		}

    
  });
});