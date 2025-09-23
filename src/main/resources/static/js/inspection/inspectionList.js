/**
 * 
 */

/* 점검 검색 */
document.addEventListener("DOMContentLoaded", function () {
    const searchType = document.getElementById("searchType");
    const keywordInput = document.getElementById("keywordInput");
    const typeSelect = document.getElementById("typeSelect");
    const resultSelect = document.getElementById("resultSelect");

    function toggleInputs() {
        // 모두 숨기기
        keywordInput.classList.add("d-none");
        typeSelect.classList.add("d-none");
        resultSelect.classList.add("d-none");

        // name 속성 제거 (서버로 안 넘어가게)
        keywordInput.removeAttribute("name");
        typeSelect.removeAttribute("name");
        resultSelect.removeAttribute("name");

        // 선택된 searchType 에 따라 보이고 name="keyword" 부여
        if (searchType.value === "ride" || searchType.value === "staff") {
            keywordInput.classList.remove("d-none");
            keywordInput.setAttribute("name", "keyword");
        } else if (searchType.value === "type") {
            typeSelect.classList.remove("d-none");
            typeSelect.setAttribute("name", "keyword");
        } else if (searchType.value === "result") {
            resultSelect.classList.remove("d-none");
            resultSelect.setAttribute("name", "keyword");
        }
    }

    // 초기 로드 시 상태 맞추기
    toggleInputs();

    // 옵션 변경 시 상태 변경
    searchType.addEventListener("change", toggleInputs);
});
