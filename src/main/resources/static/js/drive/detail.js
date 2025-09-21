console.log("detail.js 연결됨")

const dropZone = document.getElementById("dropZone");
const fileInput = document.getElementById("fileInput");
const fileName = document.getElementById("fileName");
const uploadFrm = document.getElementById("uploadFrm");

// 클릭하면 파일 선택창 열기
dropZone.addEventListener("click", () => fileInput.click());

// 드래그한 파일이 들어왔을 때 스타일 변경
dropZone.addEventListener("dragover", (e) => {
	e.preventDefault();
	dropZone.classList.add("bg-light");
});

dropZone.addEventListener("dragleave", () => {
	dropZone.classList.remove("bg-light");
});

// 드롭하면 하여 주입
dropZone.addEventListener("drop", (e) => {
	e.preventDefault();
	dropZone.classList.remove("bg-light");
	if (e.dataTransfer.files.length) {
		fileInput.files = e.dataTransfer.files; // 주입
		fileName.textContent = "선택된 파일: " + fileInput.files[0].name;
	}
});

// 파일 선택 (사용자가 직접 넣음)
fileInput.addEventListener("change", () => {
	if (fileInput.files.length) {
		fileName.textContent = "선택된 파일: " + fileInput.files[0].name;
	}
});

// 
uploadFrm.addEventListener("submit", (e) => {
	e.preventDefault();
	if (!fileInput.files.length) {
		Swal.fire({
	        text: "파일을 선택하세요!",
	        icon: "warning",
	        confirmButtonColor: "#191919",
	        confirmButtonText: "확인"
  	    });
		return;
	}
	
	// TO-DO 나중에 지울것
	const jobCode = document.getElementById("jobCode").value;
	console.log("업로드 파일:", fileInput.files[0].name);
	console.log("권한:", jobCode);
	uploadFrm.submit();
});