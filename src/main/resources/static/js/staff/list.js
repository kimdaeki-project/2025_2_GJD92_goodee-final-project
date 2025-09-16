function movePage(page) {
	if (page == null) page = 0
	const search = document.querySelector("#searchText").value
	
	if (search != null && search != "") {
		location.href = `/staff?page=${page}&search=${search}`		
	} else {
		location.href = `/staff?page=${page}`
	}
}