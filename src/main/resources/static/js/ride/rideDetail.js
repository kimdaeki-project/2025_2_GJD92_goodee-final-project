// 어트랙션 삭제
function ridedelete(rideCode) {
	Swal.fire({
	  text: "어트랙션을 삭제하시겠습니까?",
	  icon: "question",
	  showCancelButton: true,
	  confirmButtonColor: "#191919",
	  cancelButtonColor: "#FFFFFF",
	  confirmButtonText: "삭제",
		cancelButtonText: "취소",
		customClass: {
					cancelButton: 'my-cancel-btn'
				}
	})	.then((result) => {
	      if (result.isConfirmed) {
	        // POST 요청으로 삭제
	        const form = document.createElement("form");
	        form.method = "post";
	        form.action = `/ride/${rideCode}/delete`;
	        document.body.appendChild(form);
	        form.submit();
	      }
	    });
	}
	
