/**
 * 
 */
const form = document.querySelector('#form');
const btn = document.querySelector('#btn-delete');

btn.addEventListener('click', () => {
	const check = window.confirm('해당 게시글을 삭제하시겠습니까?');
	if (check) {
		form.submit();
	}
});