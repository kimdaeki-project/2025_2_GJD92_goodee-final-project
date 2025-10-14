console.log("detail.js 연결됨")

const dropZone = document.getElementById("dropZone");
const fileInput = document.getElementById("fileInput");
const fileName = document.getElementById("fileName");
const uploadFrm = document.getElementById("uploadFrm");
const checkAll = document.getElementById("checkAll");
const checkBoxes = document.querySelectorAll(".checkBoxes");
const btnDownloadFile = document.getElementById("btnDownloadFile");
const btnDeleteFile = document.getElementById("btnDeleteFile");
const fileTypeSelect = document.getElementById("fileTypeSelect");
const frmSearch = document.getElementById("frmSearch");
const fileType = document.getElementById("fileType");

window.addEventListener("load", () => {
  const COLLAPSE_KEY = "openDriveCollapses";
  const DEFAULT_OPEN = ["collapseMyDrive", "collapseShareDrive"];

  // 1️⃣ localStorage 초기화 (최초 1회만)
  let opened = JSON.parse(localStorage.getItem(COLLAPSE_KEY));

  if (!opened) {
    // 최초 접근 시에만 기본 드라이브 2개를 저장
    opened = [...DEFAULT_OPEN];
    localStorage.setItem(COLLAPSE_KEY, JSON.stringify(opened));
  }

  // 2️⃣ collapse 열리고 닫힐 때 localStorage 갱신
  document.querySelectorAll('.collapse').forEach(collapse => {
    collapse.addEventListener('show.bs.collapse', () => {
      let openedNow = JSON.parse(localStorage.getItem(COLLAPSE_KEY)) || [];
      if (!openedNow.includes(collapse.id)) {
        openedNow.push(collapse.id);
        localStorage.setItem(COLLAPSE_KEY, JSON.stringify(openedNow));
      }
    });

    collapse.addEventListener('hide.bs.collapse', () => {
      let openedNow = JSON.parse(localStorage.getItem(COLLAPSE_KEY)) || [];
      openedNow = openedNow.filter(id => id !== collapse.id);
      localStorage.setItem(COLLAPSE_KEY, JSON.stringify(openedNow));
    });
  });

  // 3️⃣ 페이지 로드 시 localStorage 기준으로 표시
  const openedFinal = JSON.parse(localStorage.getItem(COLLAPSE_KEY)) || [];
  document.querySelectorAll('.collapse').forEach(collapse => {
    if (openedFinal.includes(collapse.id)) {
      collapse.classList.add('show');   // 애니메이션 없이 즉시 표시
    } else {
      collapse.classList.remove('show');
    }
  });
});

// 클릭하면 파일 선택창 열기
dropZone.addEventListener("click", () => fileInput.click());

dropZone.addEventListener("dragover", (e) => {
	e.preventDefault();
	dropZone.classList.add("bg-light");
});

dropZone.addEventListener("dragleave", () => {
	dropZone.classList.remove("bg-light");
});

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
	
	uploadFrm.submit();
});

document.addEventListener("hidden.bs.modal", () => {
	if (fileInput.files.length) {
		fileName.textContent = "파일을 선택하세요!";
		fileInput.value = ""; // 파일 비우기
	}
	document.getElementById("jobCode").value = "1202"; // select태그를 다시 전체로 바꿈 
})


checkAll.addEventListener("change", () => {
	checkBoxes.forEach(function (checkBox){
			checkBox.checked = checkAll.checked;
		})
	changeButtonStatus();
})

fileTypeSelect.addEventListener("change", () => {
	const selectedType = fileTypeSelect.value; 	
	fileType.value = selectedType;  	
	frmSearch.submit();
})

checkBoxes.forEach(function (checkBox){
	checkBox.addEventListener("change", () => {
		const isAllChecked = Array.from(checkBoxes).every(cb => cb.checked)
		checkAll.checked = isAllChecked;
		changeButtonStatus();
	})
})

function changeButtonStatus() {
	const anyChecked = Array.from(checkBoxes).some(cb => cb.checked);
	btnDownloadFile.disabled = !anyChecked;
	btnDeleteFile.disabled = !anyChecked;
}

function deleteFile(driveNum) {
	Swal.fire({
		title: "정말 삭제하시겠습니까?",
		text: '삭제된 파일은 복구되지 않습니다.',
		icon: "error",
		showCancelButton: true,
		confirmButtonColor: "#191919",
		cancelButtonColor: "#FFFFFF",
		confirmButtonText: "삭제",
		cancelButtonText: "취소",
		customClass: {
			cancelButton: 'my-cancel-btn'
		}
	}).then((result) => {
		if (!result.isConfirmed) {
			return;
		}
		const checkedFiles = Array.from(checkBoxes).filter(cb => cb.checked);
		const attachNums = checkedFiles.map(checkedFile => checkedFile.value);

		let params = new URLSearchParams();
		params.append("attachNums", attachNums)

		fetch(`/drive/${driveNum}/delete`, {
			method: 'post',
			body: params
		})
		.then(r => r.json())
		.then(r => {
			console.log(r)
			if(r) {
				Swal.fire({
					text: "파일 삭제 완료!",
					icon: "success",
					confirmButtonColor: "#191919",
					confirmButtonText: "확인"
				})
				.then((result) => {
					if (result) location.href = "/drive/" + driveNum;
				})
			} else {
				Swal.fire({
					text: "파일 삭제 실패!",
					icon: "error",
					confirmButtonColor: "#191919",
					confirmButtonText: "확인"
				})
			}
		})
		.catch(e => {
			console.log("실패", e)
		});
	});
}


function downloadFile(driveNum) {
	const checkedFiles = Array.from(checkBoxes).filter(cb => cb.checked);
	const attachNums = checkedFiles.map(cb => cb.value);

	if (attachNums.length === 1) {		// 단일 파일
		location.href = `/drive/${driveNum}/downloadDocument?attachNums=${attachNums[0]}`;
	} else {		// ZIP 다운로드
		const query = attachNums.map(num => "attachNums=" + num).join("&");
		location.href = `/drive/${driveNum}/downloadDocument?` + query;
	}
}