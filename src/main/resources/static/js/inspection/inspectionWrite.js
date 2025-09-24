/**
 * 파일 미리보기
 */
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

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  const mode = form.getAttribute("data-mode"); // add | edit

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    // 1. 입력값 확인
    const requiredInputs = form.querySelectorAll("input, select");
    let emptyField = false;

    requiredInputs.forEach((el) => {
      const type = el.getAttribute("type");

      if (type === "file") {
        const existingFile = document.querySelector("#hasExistingFile");
        const hasExistingFile =
          existingFile && existingFile.value === "true";
        const isFileEmpty = !el.value;

        if (mode === "add" && isFileEmpty) {
          // 등록 시: 파일 필수
          emptyField = true;
        }

        if (mode === "edit") {
          // 수정 시: 기존 파일 없고 새 파일도 업로드 안 하면 오류
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
        confirmButtonText: "확인",
      });
      return;
    }

    // 2. 등록(add) 또는 수정(edit) 확인 모달
    if (mode === "add") {
      Swal.fire({
        text: "어트랙션 점검 기록을 등록하시겠습니까?",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "등록",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          e.target.submit(); // 네이티브 submit
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
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          e.target.submit();
        }
      });
    }
  });
});
