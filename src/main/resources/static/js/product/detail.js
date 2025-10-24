document.addEventListener("DOMContentLoaded", () => {
  const modalEl = document.getElementById('productDetailModal');
  const productDetailModal = new bootstrap.Modal(modalEl);
  const productDetailTable = document.getElementById('productDetailTable');
  const productDetailAttach = document.getElementById("productDetailAttach");
  const buttonBox = document.getElementById("productModalButtons");

  // 각 테이블 row에 클릭 이벤트 부여
  document.querySelectorAll("table tbody tr").forEach(row => {
    row.addEventListener("click", () => {
      const anchor = row.querySelector("a");
      const productCode = anchor.getAttribute("href").split("/").pop();

      fetch(`/product/${productCode}`)
        .then(res => res.json())
        .then(data => {
          console.log("📦 물품 상세:", data);

          // 이미지
		  productDetailAttach.innerHTML =
			  data.productAttachmentDTO &&
			  data.productAttachmentDTO.attachmentDTO &&
			  data.productAttachmentDTO.attachmentDTO.savedName
			    ? `<img width="300" height="300" style="object-fit: contain;" src="/file/product/${data.productAttachmentDTO.attachmentDTO.savedName}"/>`
			    : "";

          // 테이블
          productDetailTable.innerHTML = `
            <tr><th>물품번호</th><td>${data.productCode}</td></tr>
            <tr><th>등록일자</th><td>${data.productDate}</td></tr>
            <tr><th>물품타입명</th><td>${data.productTypeDTO.productTypeName}</td></tr>
            <tr><th>물품명</th><td>${data.productName}</td></tr>
            <tr><th>규격</th><td>${data.productSpec || ''}</td></tr>
            <tr style="border-bottom: 1px solid #dee2e6;"><th>재고수량</th><td>${data.productAmount}</td></tr>
          `;

            document.getElementById("productUpdateBtn").onclick = () => {
              location.href = `/product/${data.productCode}/update`;
            };
            document.getElementById("productDeleteForm").action = `/product/${data.productCode}/delete`;

            productDetailModal.show();
        })
        .catch(err => {
          console.error("❌ 상세 정보 불러오기 실패:", err);
          alert("상세 정보를 불러오지 못했습니다.");
        });
    });
  });
});