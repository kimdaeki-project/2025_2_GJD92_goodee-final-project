/**
 * footer.jsp
 */
function openMessenger() {
    window.open(
        "/msg",
        "MessengerWindow",
        "width=400,height=500,resizable=no,scrollbars=yes"
    );
}

function plusOneChatCount() {
	let bdg = document.querySelector('.badge-footer-display');
	let count = parseInt(bdg.innerText) + 1;
	if (count > 9) {
		bdg.innerText = '9+';
	} else {
		bdg.innerText = count;
	}
}