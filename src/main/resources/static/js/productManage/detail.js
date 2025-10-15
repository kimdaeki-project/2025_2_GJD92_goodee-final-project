document.addEventListener("DOMContentLoaded", () => {
  const modalEl = document.getElementById('pmDetailModal');
  const pmDetailModal = new bootstrap.Modal(modalEl);
  const pmDetailTable = document.getElementById('pmDetailTable');
//  const pmDetailAttach = document.getElementById("pmDetailAttach");
  const buttonBox = document.getElementById("pmModalButtons");

  // 각 테이블 row에 클릭 이벤트 부여
  document.querySelectorAll("table tbody tr").forEach(row => {
    row.addEventListener("click", () => {
      const anchor = row.querySelector("a");
      const pmNum = anchor.getAttribute("href").split("/").pop();

      fetch(`/productManage/${pmNum}`)
        .then(res => res.json())
        .then(data => {
          console.log("📦 입출고내역 상세:", data);

//          // 이미지
//          pmDetailAttach.innerHTML = data.productDTO.productAttachmentDTO?.attachmentDTO?.savedName
//            ? `<img width="400" height="400" style="object-fit: clip;" src="/file/product/${data.productDTO.productAttachmentDTO.attachmentDTO.savedName}"/>`
//            : "";

          // 테이블
          pmDetailTable.innerHTML = `
            <tr><th>입출고번호</th><td>${data.pmNum}</td></tr>
            <tr><th>등록일자</th><td>${data.pmDate}</td></tr>
            <tr><th>작성자</th><td>${data.staffDTO ? data.staffDTO.staffName : undefined}</td></tr>
            <tr><th>물품번호</th><td>${data.productDTO ? data.productDTO.productCode : undefined}</td></tr>
            <tr><th>물품타입</th><td>${data.productDTO && data.productDTO.productTypeDTO ? data.productDTO.productTypeDTO.productTypeName : undefined}</td></tr>
            <tr><th>물품명</th><td>${data.productDTO ? data.productDTO.productName : undefined}</td></tr>
            <tr><th>등록수량</th><td>${data.pmAmount}</td></tr>
            <tr style="border-bottom: 1px solid #dee2e6;"><th>잔여수량</th><td>${data.pmRemainAmount}</td></tr>
          `;

          // 로그인 사용자와 작성자 비교 후 버튼 표시
          const writerCode = data.staffDTO ? data.staffDTO.staffCode : undefined;
          console.log("👤 로그인:", loginStaffCode, "| 작성자:", writerCode);

          if (		  typeof loginStaffCode !== 'undefined' &&
		    typeof writerCode !== 'undefined' &&
		    String(loginStaffCode) === String(writerCode)) {
				buttonBox.classList.remove("d-none");
            buttonBox.style.display = "block";
            document.getElementById("pmUpdateBtn").onclick = () => {
              location.href = `/productManage/${data.pmNum}/update`;
            };
            document.getElementById("pmDeleteForm").action = `/productManage/${data.pmNum}/delete`;
          } else {
			buttonBox.classList.add("d-none");
            buttonBox.style.display = "none";
          }

          pmDetailModal.show();
        })
        .catch(err => {
          console.error("❌ 상세 정보 불러오기 실패:", err);
          alert("상세 정보를 불러오지 못했습니다.");
        });
    });
  });
});