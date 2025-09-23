function movePage(page) {
	if (page == null) page = 0
	const search = document.querySelector("#searchText").value
	
	if (search != null && search != "") {
		location.href = `/product?page=${page}&search=${search}`		
	} else {
		location.href = `/product?page=${page}`
	}
}