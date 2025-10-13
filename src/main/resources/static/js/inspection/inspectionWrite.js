// ==============================
// 파일 사이즈 & 이미지 타입 제한
// ==============================
const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
const ALLOWED_TYPES = ["image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp"];

// ==============================
// 파일 미리보기
// ==============================
document.querySelector("#attach").addEventListener("change", (event) => {
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
      document.querySelector("#preview").src = "";
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
      document.querySelector("#preview").src = "";
      return;
    }

    const reader = new FileReader();
    reader.onload = function (event) {
      document.querySelector("#preview").src = event.target.result;
    };
    reader.readAsDataURL(file);
  } else {
    document.querySelector("#preview").src = "";
  }
});

// 등록 / 수정 폼 검증
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  // DTO 기반 name 속성 반영
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
        confirmButtonColor: "#191919",
        confirmButtonText: "확인"
      });
      return;
    }

	 // 2. 날짜 유효성 검사
	  const startDate = new Date(isptStartInput.value);
	  const endDate = new Date(isptEndInput.value);
	  if (startDate > endDate) {
	    Swal.fire({
	      text: "점검 시작일은 종료일보다 늦을 수 없습니다.",
	      icon: "error",
	      confirmButtonColor: "#191919",
	      confirmButtonText: "확인"
	    });
	    return;
	  }

	  // 3. 최종 등록/수정 확인
	  if (mode === "add") {
	    Swal.fire({
	      text: "어트랙션 점검 기록을 등록하시겠습니까?",
	      icon: "question",
	      showCancelButton: true,
	      confirmButtonColor: "#191919",
	      cancelButtonColor: "#FFFFFF",
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
	      confirmButtonColor: "#191919",
	      cancelButtonColor: "#FFFFFF",
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