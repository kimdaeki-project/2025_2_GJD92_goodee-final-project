/**
 * /notice/write.jsp 전용 js 파일
 * 파일 업로드 포함
 */
const btn = document.querySelector('#btn-write');
const form = document.querySelector('#form');
const cancelBtn = document.querySelector('#btn-cancel');
const input = document.querySelector('#fileInput');
const data = btn.getAttribute('data-kind');
const noticePinned = document.querySelector('#noticePinned').checked;

btn.addEventListener('click', () => {
	
	const noticeTitle = document.querySelector('#noticeTitle').value.trim();
	const noticeContent = document.querySelector('#noticeContent').value.trim();
	if (noticeTitle.length === 0) {
		Swal.fire({
			text: "제목을 입력해주세요.",
			icon: "warning",
			showCancelButton: false,
			confirmButtonColor: "#191919",
			confirmButtonText: "확인"
		});
		return;
	} else if (noticeContent.length === 0) {
		Swal.fire({
			text: "내용을 입력해주세요.",
			icon: "warning",
			showCancelButton: false,
			confirmButtonColor: "#191919",
			confirmButtonText: "확인"
		})
		return;
	}
	
	const dt = new DataTransfer();
	newFiles.forEach(f => dt.items.add(f));
	input.files = dt.files;
	if (data == 'write') {
		const pinned = document.getElementById("noticePinned");
		form.setAttribute('action', '/notice/write');
		
		if (pinned.checked) {
			fetch('/notice/pinned', {
				method: 'POST'
			})
			.then(response => response.json())
			.then(response => {
				if (response) {
					Swal.fire({
						text: "상단고정은 최대 5개까지 가능합니다.",
						icon: "warning",
						showCancelButton: false,
						confirmButtonColor: "#191919",
						confirmButtonText: "확인"
					}).then(result => console.log(result));
				} else {
					form.submit();
				}
			});			
		} else {
			form.submit();
		}
	} else if (data == 'edit') {
		let deleteFiles = document.querySelector('#deleteFiles');
		const pinned = document.getElementById("noticePinned");
		deleteFiles.value = deleteExistingFiles.join(",");
		form.setAttribute('action', './edit');
		
		if (noticePinned && pinned.checked) {
			form.submit();
		} else if (!noticePinned && pinned.checked) {
			fetch('/notice/pinned', {
				method: 'POST'
			})
			.then(response => response.json())
			.then(response => {
				if (response) {
					Swal.fire({
						text: "상단고정은 최대 5개까지 가능합니다.",
						icon: "warning",
						showCancelButton: false,
						confirmButtonColor: "#191919",
						confirmButtonText: "확인"
					}).then(result => console.log(result));
				} else {
					form.submit();
				}
			});
		} else {
			form.submit();
		}
	}
});

cancelBtn.addEventListener('click', () => {
	
	if (data == 'edit') {
		if (document.querySelector('#noticeTmp').value != 'true') {
			location.href = "/notice";			
		} else {			
			location.href = "/notice/temp";			
		}
	} else {
		Swal.fire({
			text: "게시글을 임시저장하시겠습니까?",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#191919",
			confirmButtonText: "확인",
			cancelButtonText: "취소",
			cancelButtonColor: "#FFFFFF",
			customClass: {
		    	cancelButton: 'my-cancel-btn'
		  	}
		}).then(result => {
			if (result.isConfirmed) {
				const noticeTitle = document.querySelector('#noticeTitle').value.trim();
				const noticeContent = document.querySelector('#noticeContent').value.trim();
				if (noticeTitle.length === 0) {
					Swal.fire({
						text: "제목을 입력해주세요.",
						icon: "warning",
						showCancelButton: false,
						confirmButtonColor: "#191919",
						confirmButtonText: "확인"
					});
					return;
				} else if (noticeContent.length === 0) {
					Swal.fire({
						text: "내용을 입력해주세요.",
						icon: "warning",
						showCancelButton: false,
						confirmButtonColor: "#191919",
						confirmButtonText: "확인"
					})
					return;
				}
	
				const dt = new DataTransfer();
				newFiles.forEach(f => dt.items.add(f));
				input.files = dt.files;
				form.setAttribute('action', '/notice/temp');
				form.submit();
			} else {
				location.href = "/notice";
			}
		});
	}
	
});
// -------------------------------------------------- //
const existingFiles = window.existingFiles || [];
const newFiles = [];
const deleteExistingFiles = [];

(function(){
    const input = document.getElementById('fileInput');
    const pcBtn = document.getElementById('pcBtn');
    const list = document.getElementById('fileList');

    pcBtn.addEventListener('click', () => input.click());

    function humanFileSize(size){
        if(size === 0) return '0 B';
        const i = Math.floor(Math.log(size) / Math.log(1024));
        const sizes = ['B','KB','MB','GB','TB'];
        const value = size / Math.pow(1024, i);
        return `${Math.round(value * 10) / 10} ${sizes[i]}`;
    }

    function renderList(){
        list.innerHTML = '';

        if(existingFiles.length === 0 && newFiles.length === 0){
            list.innerHTML = '<div class="text-muted text-center p-3 border rounded bg-white">선택된 파일이 없습니다.</div>';
            return;
        }

        // 기존 파일 렌더링
		existingFiles.forEach((file, idx) => {
		    const card = document.createElement('div');
		    card.className = 'card file-card shadow-sm p-2 d-flex flex-row align-items-center';

		    const thumbWrap = document.createElement('div');
		    thumbWrap.style.width = '64px';
		    thumbWrap.style.height = '64px';
		    thumbWrap.className = 'me-3';

		    // 이미지인지 확인
		    if(file.name.match(/\.(jpg|jpeg|png|gif|bmp|webp)$/i)){
		        const img = document.createElement('img');
		        img.src = file.url;
		        img.style.width = '100%';
		        img.style.height = '100%';
		        img.style.objectFit = 'cover';
		        thumbWrap.appendChild(img);
		    } else {
		        thumbWrap.innerHTML = `<div class="d-flex justify-content-center align-items-center h-100 w-100 bg-light text-muted fw-bold">${(file.name.split('.').pop() || '').toUpperCase()}</div>`;
		    }

		    const meta = document.createElement('div');
		    meta.innerHTML = `<div>${file.name}ㆍ<span class="text-muted">${humanFileSize(file.size)}</span></div>`;

		    const removeBtn = document.createElement('button');
		    removeBtn.textContent = '제거';
		    removeBtn.type = 'button';
		    removeBtn.className = 'btn btn-sm btn-outline-danger file-remove';
		    removeBtn.onclick = () => {
				deleteExistingFiles.push(file.attachNum);
		        existingFiles.splice(idx,1);
		        renderList();
		    };

		    card.appendChild(thumbWrap);
		    card.appendChild(meta);
		    card.appendChild(removeBtn);

		    list.appendChild(card);
		});

        // 새 파일 렌더링
        newFiles.forEach((file, idx) => {
            const card = document.createElement('div');
            card.className = 'card file-card shadow-sm p-2 d-flex flex-row align-items-center';

            const thumbWrap = document.createElement('div');
            thumbWrap.style.width = '64px';
            thumbWrap.style.height = '64px';
            thumbWrap.className = 'me-3';

            if(file.type && file.type.startsWith('image/')){
                const img = document.createElement('img');
                img.src = URL.createObjectURL(file);
                img.style.width = '100%';
                img.style.height = '100%';
                img.style.objectFit = 'cover';
                thumbWrap.appendChild(img);
            } else {
                thumbWrap.innerHTML = `<div class="d-flex justify-content-center align-items-center h-100 w-100 bg-light text-muted fw-bold">${(file.name.split('.').pop() || '').toUpperCase()}</div>`;
            }

            const meta = document.createElement('div');
			meta.innerHTML = `<div>${file.name}ㆍ<span class="text-muted">${humanFileSize(file.size)}</span></div>`;

            const removeBtn = document.createElement('button');
            removeBtn.textContent = '제거';
            removeBtn.type = 'button';
            removeBtn.className = 'btn btn-sm btn-outline-danger file-remove';
            removeBtn.onclick = () => {
                newFiles.splice(idx, 1);
                renderList();
            };

            card.appendChild(thumbWrap);
            card.appendChild(meta);
            card.appendChild(removeBtn);

            list.appendChild(card);
        });
    }

    input.addEventListener('change', (e) => {
        const added = Array.from(e.target.files);
        added.forEach(f => {
            const exists = newFiles.some(existing => 
                existing.name === f.name && 
                existing.size === f.size && 
                existing.lastModified === f.lastModified
            );
            if(!exists) newFiles.push(f);
        });
        input.value = '';
        renderList();
    });

    window.addEventListener('DOMContentLoaded', () => {
        renderList();
    });

})();
