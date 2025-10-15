// 어트랙션 점검 기록 삭제
function deleteInspection(isptNum) {
	Swal.fire({
	  text: "어트랙션 점검 기록을 삭제하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#191919",
	  cancelButtonColor: "#FFFFFF",
	  confirmButtonText: "삭제",
		cancelButtonText: "취소"
	})	.then((result) => {
	      if (result.isConfirmed) {
	        // POST 요청으로 삭제
	        const form = document.createElement("form");
	        form.method = "post";
	        form.action = `/inspection/${isptNum}/delete`;
	        document.body.appendChild(form);
	        form.submit();
	      }
	    });
	}
	
