function movePage(page) {
	if (page == null) page = 0
	const search = document.querySelector("#searchText").value
	const url = new URL(location.href)
	url.searchParams.set("page", page)
	
	if (search != null && search != "") {
		url.searchParams.set("search", search)
		location.href = url.toString()
	} else {
		location.href = url.toString()
	}
}

function aprvFilter(btn) {
	const thisId = btn.getAttribute("id")
	
	if (btn.classList.contains("crnt-aprv")) {
		location.href = "/approval"
	} else {
		location.href = "/approval/" + thisId
	}
}

document.addEventListener('DOMContentLoaded', () => {
	const url = location.pathname
	
	if (url.includes("request")) {
		document.querySelector("#request").classList.add("bg-gradient-dark", "text-white", "crnt-aprv")
		Array.from(document.querySelector("#request").children).forEach((child) => {
			child.classList.add("text-white")
		})
	}
	
	if (url.includes("ready")) {
		document.querySelector("#ready").classList.add("bg-gradient-dark", "text-white", "crnt-aprv")
		Array.from(document.querySelector("#ready").children).forEach((child) => {
			child.classList.add("text-white")
		})
	}
	
	if (url.includes("mine")) {
		document.querySelector("#mine").classList.add("bg-gradient-dark", "text-white", "crnt-aprv")
		Array.from(document.querySelector("#mine").children).forEach((child) => {
			child.classList.add("text-white")
		})
	}
	
	if (url.includes("finish")) {
		document.querySelector("#finish").classList.add("bg-gradient-dark", "text-white", "crnt-aprv")
		Array.from(document.querySelector("#finish").children).forEach((child) => {
			child.classList.add("text-white")
		})
	}
	
})

function openApprovalSign() {
	window.open("/approval/sign", "_blank", "width=300, height=400, top=250, left=400, menubar=no, toolbar=no, location=no, status=no")
}