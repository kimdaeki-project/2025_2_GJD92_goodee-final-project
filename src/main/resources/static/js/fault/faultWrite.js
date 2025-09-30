/**
 * 어트랙션 고장 신고 등록/수정 JS
 */

// 파일 미리보기 
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  if (!form) return; // form이 없으면 종료

  // 파일 미리보기 (update.jsp 전용)
  const attachInput = document.querySelector("#attach");
  if (attachInput) {
    attachInput.addEventListener("change", (event) => {
      const preview = document.querySelector("#preview");
      const noImageText = document.querySelector("#noImageText");

      if (event.target.files && event.target.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
          preview.src = e.target.result;
          preview.style.display = "block";
          if (noImageText) noImageText.style.display = "none";
        };
        reader.readAsDataURL(event.target.files[0]);
      } else {
        preview.src = "";
        preview.style.display = "none";
        if (noImageText) noImageText.style.display = "block";
      }
    });
  }

  // ==============================
  // 등록/수정 폼 검증 + 모달 확인
  // ==============================
  form.addEventListener("submit", function (e) {
    e.preventDefault();

    let emptyField = false;

    // 1. 입력값 확인 (hidden 제외)
    const requiredInputs = form.querySelectorAll("input:not([type=hidden]), select, textarea");
    requiredInputs.forEach((el) => {
      const type = el.getAttribute("type");

      if (type === "file") {
        // 수정 페이지일 경우: 기존 파일 없으면 필수
        const existingFile = document.querySelector("#hasExistingFile");
        const hasExistingFile = existingFile && existingFile.value === "true";
        const isFileEmpty = !el.value || !el.value.trim();

        if (!hasExistingFile && isFileEmpty) {
          emptyField = true;
        }
      } else {
        if (!el.value || !el.value.trim()) {
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
	
	// 2. 날짜 유효성 검사 (오늘 이후 불가)
	  const dateInput = form.querySelector("input[name='faultDate']");
	  if (dateInput && dateInput.value) {
	    const selectedDate = new Date(dateInput.value);
	    const today = new Date();
	    today.setHours(0, 0, 0, 0); // 오늘 0시 기준

	    if (selectedDate > today) {
	      Swal.fire({
	        text: "날짜 양식이 올바르지 않습니다.",
	        icon: "error",
	        confirmButtonColor: "#3085d6",
	        confirmButtonText: "확인"
	      });
	      return;
	    }
	  }

    // 2. 최종 등록/수정 확인 모달
    const isUpdatePage = !!attachInput; // update.jsp에는 첨부파일 input 존재
    Swal.fire({
      text: isUpdatePage
        ? "어트랙션 고장 신고를 수정하시겠습니까?"
        : "어트랙션 고장 신고를 등록하시겠습니까?",
      icon: "question",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: isUpdatePage ? "수정" : "등록",
      cancelButtonText: "취소"
    }).then((result) => {
      if (result.isConfirmed) {
        form.submit();
      }
    });
  });
});
