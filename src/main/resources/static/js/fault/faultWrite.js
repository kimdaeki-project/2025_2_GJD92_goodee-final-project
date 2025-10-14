// 어트랙션 고장 신고 등록/수정 JS
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  if (!form) return; // form이 없으면 종료

  const attachInput = document.querySelector("#attach");
  const preview = document.querySelector("#preview");
  const noImageText = document.querySelector("#noImageText");

  // 파일 사이즈 & 이미지 타입 제한
  const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
  const ALLOWED_TYPES = ["image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp"];

  // 파일 미리보기 (등록/수정 공통)
  if (attachInput) {
    attachInput.addEventListener("change", (event) => {
      const file = event.target.files[0];

      if (file) {
        // 파일 크기 검사
        if (file.size > MAX_FILE_SIZE) {
          Swal.fire({
            text: "파일 크기는 5MB 이하여야 합니다.",
            icon: "error",
            confirmButtonColor: "#191919",
            confirmButtonText: "확인"
          });
          event.target.value = "";
          if (preview) preview.src = "";
          if (preview) preview.style.display = "none";
          if (noImageText) noImageText.style.display = "block";
          return;
        }

        // 이미지 타입 검사
        if (!ALLOWED_TYPES.includes(file.type)) {
          Swal.fire({
            text: "이미지 파일(jpg, jpeg, png, gif, bmp, webp)만 업로드 가능합니다.",
            icon: "error",
            confirmButtonColor: "#191919",
            confirmButtonText: "확인"
          });
          event.target.value = "";
          if (preview) preview.src = "";
          if (preview) preview.style.display = "none";
          if (noImageText) noImageText.style.display = "block";
          return;
        }

        // 미리보기 출력
        const reader = new FileReader();
        reader.onload = function (e) {
          if (preview) {
            preview.src = e.target.result;
            preview.style.display = "block";
          }
          if (noImageText) noImageText.style.display = "none";
        };
        reader.readAsDataURL(file);
      } else {
        if (preview) {
          preview.src = "";
          preview.style.display = "none";
        }
        if (noImageText) noImageText.style.display = "block";
      }
    });
  }

  // 등록/수정 폼 검증 + 모달 확인
  form.addEventListener("submit", function (e) {
    e.preventDefault();

    let emptyField = false;

    // 입력값 확인 (hidden 제외)
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
        confirmButtonColor: "#191919",
        confirmButtonText: "확인"
      });
      return;
    }
	
	// 날짜 유효성 검사 (오늘 이후 불가)
	  const dateInput = form.querySelector("input[name='faultDate']");
	  if (dateInput && dateInput.value) {
	    const selectedDate = new Date(dateInput.value);
	    const today = new Date();
	    today.setHours(0, 0, 0, 0); // 오늘 0시 기준

	    if (selectedDate > today) {
	      Swal.fire({
	        text: "날짜 양식이 올바르지 않습니다.",
	        icon: "error",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
	      });
	      return;
	    }
	  }

    // 최종 등록/수정 확인 모달
    const isUpdatePage = !!attachInput; // update.jsp에는 첨부파일 input 존재
    Swal.fire({
      text: isUpdatePage
        ? "어트랙션 고장 신고를 수정하시겠습니까?"
        : "어트랙션 고장 신고를 등록하시겠습니까?",
      icon: "question",
      showCancelButton: true,
      confirmButtonColor: "#191919",
      cancelButtonColor: "#FFFFFF",
      confirmButtonText: isUpdatePage ? "수정" : "등록",
      cancelButtonText: "취소"
    }).then((result) => {
      if (result.isConfirmed) {
        form.submit();
      }
    });
  });
});
