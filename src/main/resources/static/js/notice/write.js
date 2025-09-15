/**
 * 
 */
const btn = document.querySelector('#btn-write');
const form = document.querySelector('#form');

btn.addEventListener('click', () => {
	const data = btn.getAttribute('data-kind');
	if (data == 'write') {
		form.setAttribute('action', '/notice/write');
		form.submit();
	} else if (data == 'edit') {
		form.setAttribute('action', './edit');
		form.submit();
	}
});