/**
 * 
 */

// 파일 미리보기
document.querySelector("#attach").addEventListener("change", (event) => {
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




// 등록 / 수정 폼 검증
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
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
        const isFileEmpty = !el.value || !el.value.trim();

        if (mode === "add" && isFileEmpty) {
          emptyField = true;
        }
        if (mode === "edit" && !hasExistingFile && isFileEmpty) {
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

    // 모든 검증 통과 → 전송
    form.submit();
  });
});