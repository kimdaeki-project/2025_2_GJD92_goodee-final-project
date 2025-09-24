// ==============================
// 파일 미리보기
// ==============================
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
});


// ==============================
// 등록 / 수정 폼 검증
// ==============================
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  // DTO 기반 name 속성 반영
  const rideCodeInput = document.querySelector("input[name='rideDTO.rideCode']");
  const staffCodeInput = document.querySelector("input[name='staffDTO.staffCode']");
  const isptStartInput = document.querySelector("input[name='isptStart']");
  const isptEndInput = document.querySelector("input[name='isptEnd']");
  const mode = form.getAttribute("data-mode"); // add / edit

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    // ==============================
    // 1. 입력값 확인 (빈칸 + 첨부파일)
    // ==============================
    const requiredInputs = form.querySelectorAll("input, select");
    let emptyField = false;

    requiredInputs.forEach((el) => {
      const type = el.getAttribute("type");

      if (type === "file") {
        const existingFile = document.querySelector("#hasExistingFile");
        const hasExistingFile = existingFile && existingFile.value === "true";
        const isFileEmpty = !el.value.trim();

        if (mode === "add" && isFileEmpty) {
          emptyField = true;
        }
        if (mode === "edit" && !hasExistingFile && isFileEmpty) {
          emptyField = true;
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

    // ==============================
    // 2. 날짜 유효성 검사
    // ==============================
    const startDate = new Date(isptStartInput.value);
    const endDate = new Date(isptEndInput.value);
    if (startDate > endDate) {
      Swal.fire({
        text: "점검 시작일은 종료일보다 늦을 수 없습니다.",
        icon: "error",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "확인"
      });
      return;
    }

	    // ==============================
	    // 3. rideCode, staffCode 값 읽기
	    // ==============================
	    const rideCode = rideCodeInput.value.trim();
	    const staffCode = staffCodeInput.value.trim();

	    // staffCode가 숫자가 아닐 경우 즉시 차단
	    if (!/^\d+$/.test(staffCode)) {
	      Swal.fire({
	        text: "올바른 형식이 아닙니다.",
	        icon: "error",
	        confirmButtonColor: "#3085d6",
	        confirmButtonText: "확인"
	      });
	      return;
	    }

	    // ==============================
	    // 4. rideCode, staffCode 유효성 검사 (AJAX 병렬 요청)
	    // ==============================
	    Promise.all([
	      fetch("/inspection/checkRideCode?rideCode=" + rideCode).then(res => res.json()),
	      fetch("/inspection/checkStaffCode?staffCode=" + staffCode).then(res => res.json())
	    ])
	    .then(([isRideValid, isStaffValid]) => {
	      console.log("rideCode valid?", isRideValid);
	      console.log("staffCode valid?", isStaffValid);
	      console.log("mode:", mode);

	      if (!isRideValid || !isStaffValid) {
	        Swal.fire({
	          text: "올바른 형식이 아닙니다.",
	          icon: "error",
	          confirmButtonColor: "#3085d6",
	          confirmButtonText: "확인"
	        });
	        return;
	      }

	      // ==============================
	      // 5. 최종 등록/수정 확인
	      // ==============================
	      if (mode === "add") {
	        Swal.fire({
	          text: "어트랙션 점검 기록을 등록하시겠습니까?",
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
	      } else if (mode === "edit") {
	        Swal.fire({
	          text: "어트랙션 점검 기록을 수정하시겠습니까?",
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
	    })
	    .catch((err) => {
	      Swal.fire({
	        text: "검증 중 오류가 발생했습니다.",
	        icon: "error",
	        confirmButtonColor: "#3085d6",
	        confirmButtonText: "확인"
	      });
	      console.error(err);
	    });
	  });
	});