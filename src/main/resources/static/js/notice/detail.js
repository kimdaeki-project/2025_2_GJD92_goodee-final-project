/**
 * 
 */
const form = document.querySelector('#form');
const btn = document.querySelector('#btn-delete');

btn.addEventListener('click', () => {
	Swal.fire({
		text: "해당 게시글을 삭제하시겠습니까?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#191919",
		cancelButtonColor: "#FFFFFF",
		confirmButtonText: "확인",
		cancelButtonText: "취소",
		customClass: {
			cancelButton: 'my-cancel-btn'
		}
	}).then(result => {
		if (result.isConfirmed) {
			form.submit();
		}
	});
});