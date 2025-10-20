function movePage(page) {
	if (page == null) page = 0
	const search = document.querySelector("#searchText").value
	
	// 현재 경로에서 dept_code 추출
	const pathSegments = window.location.pathname.split('/');
	let deptCode = null;
	if (pathSegments.length >= 3 && !isNaN(pathSegments[2])) {
		deptCode = pathSegments[2];
	}
	
	// URL 만들기
	let url = "/address";
	if (deptCode !== null) {
		url += `/${deptCode}`;
	}

	url += `?page=${page}`;
	if (search && search.trim() !== "") {
		url += `&search=${encodeURIComponent(search)}`;
	}

	location.href = url;
	}