/**
 * /notice/write.jsp 전용 js 파일
 * 파일 업로드 포함
 */
const btn = document.querySelector('#btn-write');
const form = document.querySelector('#form');

const input = document.querySelector('#fileInput');
const filesArr = [];

btn.addEventListener('click', () => {
	const data = btn.getAttribute('data-kind');
	const dt = new DataTransfer();
	filesArr.forEach(f => dt.items.add(f));
	input.files = dt.files;
	if (data == 'write') {
		form.setAttribute('action', '/notice/write');
		form.submit();
	} else if (data == 'edit') {
		form.setAttribute('action', './edit');
		form.submit();
	}
});
// -------------------------------------------------- //
(function(){
  const input = document.getElementById('fileInput');
  const pcBtn = document.getElementById('pcBtn');
  const dropzone = document.getElementById('dropzone');
  const list = document.getElementById('fileList');
  
  pcBtn.addEventListener('click', () => input.click());

  function humanFileSize(size){
    if(size === 0) return '0 B';
    const i = Math.floor(Math.log(size) / Math.log(1024));
    const sizes = ['B','KB','MB','GB','TB'];
    const value = (size / Math.pow(1024, i));
    return `${Math.round(value * 10) / 10} ${sizes[i]}`;
  }

  function renderList(){
    list.innerHTML = '';
    filesArr.forEach((file, idx) => {
      const card = document.createElement('div');
      card.className = 'card file-card shadow-sm p-2 d-flex flex-row align-items-center';
      card.setAttribute('data-index', idx);

      const thumbWrap = document.createElement('div');
      thumbWrap.className = 'ratio ratio-1x1 rounded overflow-hidden me-3';
      thumbWrap.style.width = '64px';
      thumbWrap.style.height = '64px';

      if(file.type.startsWith('image/')){
        const img = document.createElement('img');
        img.src = URL.createObjectURL(file);
        img.onload = () => URL.revokeObjectURL(img.src);
        thumbWrap.appendChild(img);
      } else {
        thumbWrap.innerHTML = `<div class="d-flex justify-content-center align-items-center h-100 w-100 bg-light text-muted fw-bold">${(file.name.split('.').pop() || '').toUpperCase()}</div>`;
      }

      const meta = document.createElement('div');
      meta.className = 'file-meta';
      meta.innerHTML = `
        <div class="file-name">${file.name}</div>
        <div class="file-info">${file.type || '알 수 없음'} · ${humanFileSize(file.size)}</div>
      `;

      const actions = document.createElement('div');
      actions.className = 'd-flex gap-2 ms-3';

      const previewBtn = document.createElement('button');
      previewBtn.className = 'btn btn-sm btn-outline-secondary';
      previewBtn.textContent = '미리보기';
      previewBtn.type = 'button';
      previewBtn.onclick = () => {
        const url = URL.createObjectURL(file);
        window.open(url, '_blank');
      };

      const removeBtn = document.createElement('button');
      removeBtn.className = 'btn btn-sm btn-outline-danger';
      removeBtn.textContent = '제거';
      removeBtn.type = 'button';
      removeBtn.onclick = () => {
        filesArr.splice(idx,1);
        renderList();
      };

      actions.appendChild(previewBtn);
      actions.appendChild(removeBtn);

      card.appendChild(thumbWrap);
      card.appendChild(meta);
      card.appendChild(actions);

      list.appendChild(card);
    });

    if(filesArr.length === 0){
      list.innerHTML = '<div class="text-muted text-center p-3 border rounded bg-white">선택된 파일이 없습니다.</div>';
    }
  }

  input.addEventListener('change', (e) => {
    const newFiles = Array.from(e.target.files);
    newFiles.forEach(f => {
      const exists = filesArr.some(existing => existing.name === f.name && existing.size === f.size && existing.lastModified === f.lastModified);
      if(!exists) filesArr.push(f);
    });
    input.value = '';
    renderList();
  });

  ['dragenter','dragover'].forEach(ev => {
    dropzone.addEventListener(ev, (e) => {
      e.preventDefault();
      e.stopPropagation();
      dropzone.classList.add('is-dragover');
    });
  });

  ['dragleave','dragend','drop'].forEach(ev => {
    dropzone.addEventListener(ev, (e) => {
      e.preventDefault();
      e.stopPropagation();
      dropzone.classList.remove('is-dragover');
    });
  });

  dropzone.addEventListener('drop', (e) => {
    const dt = e.dataTransfer;
    if(!dt) return;
    const dropped = Array.from(dt.files || []);
    dropped.forEach(f => {
      const exists = filesArr.some(existing => existing.name === f.name && existing.size === f.size && existing.lastModified === f.lastModified);
      if(!exists) filesArr.push(f);
    });
    renderList();
  });

  dropzone.addEventListener('click', () => input.click());
  dropzone.addEventListener('keydown', (e) => {
    if(e.key === 'Enter' || e.key === ' '){
      e.preventDefault();
      input.click();
    }
  });

  renderList();
})();