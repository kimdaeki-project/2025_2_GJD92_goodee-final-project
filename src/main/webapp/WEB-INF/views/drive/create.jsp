<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>드라이브</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>

	<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
		<c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
		<div class="d-flex">
			<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="height: 92vh; min-width: 180px;">
				<div class="w-100">
					<ul class="navbar-nav">
					
						<!-- 내 드라이브 시작 -->
						<li>
							<div class="nav-link text-dark d-flex justify-content-between">
								<div class="d-flex align-items-center">
									<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">keyboard_arrow_down</i> 
									<span class="sub-nav-link-text ms-1 text-sm">내드라이브</span>
								</div>
								<div class="d-flex text-center">
									<a class="nav-link text-dark d-flex align-items-center" href="/drive/create"> 
										<i class="material-symbols-rounded opacity-5 fs-5" data-content="내드라이브">add</i>
									</a>
								</div>
							</div>
							<ul class="navbar-nav" id="drive-my">
							<!-- 반복문 -->
								<c:import url="/WEB-INF/views/drive/side-myDrive.jsp"></c:import>
							</ul>
						</li>
						<!-- 내 드라이브 끝 -->

						<!-- 공용 드라이브 시작 -->
						<li>
							<div class="nav-link text-dark d-flex align-items-center">
								<i class="material-symbols-rounded opacity-5 fs-5" data-content="공용드라이브">keyboard_arrow_down</i> 
								<span class="nav-link-text ms-1 mt-2 text-sm">공용드라이브</span>
							</div>
							<ul class="navbar-nav" id="drive-share">
								<!-- 공용 드라이브 반복문 시작 -->
								<c:import url="/WEB-INF/views/drive/side-shareDrive.jsp"></c:import>
								<!-- 공용 드라이브 반복문 끝 -->
							</ul>
						</li>
						<!-- 공용 드라이브 끝 -->
					</ul>
				</div>
			</aside>
			<section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">

				<!-- 여기에 메인 컨텐츠 작성 -->
				<div class="p-6">
					<form:form method="post" modelAttribute="driveDTO">
						<div class="d-flex align-items-center gap-1 w-50">
							<form:label path="driveName" cssClass="ms-0 me-1 mb-0 fs-6">드라이브 이름 : </form:label>
							<form:input path="driveName" cssClass="form-control w-auto" />
						</div>
						<div>
							<form:errors path="driveName" cssClass="text-danger small"></form:errors>
						</div>
						<div class="mt-4 d-flex align-items-center">
							<span class="fs-6">드라이브 공유 :</span>
							<button type="button" class="btn btn-outline-secondary p-1 rounded ms-2 mt-3" data-bs-toggle="modal" data-bs-target="#shareModal">
								<i class="material-symbols-rounded opacity-5 fs-5">group_add</i>
							</button>
						</div>
						<table class="col-6 offset-md-1 table w-50 mb-4">
							<thead>
								<tr>
									<th scope="col" colspan="3" class="text-center">추가된 사용자</th>
									<th scope="col" class="text-center">직급</th>
									<th scope="col" class="text-center">소속</th>
								</tr>
							</thead>
							<tbody class="table-group-divider text-center" id="savedStaff">
							
								<!-- 추가된 사용자 -->
								<!-- 추가된 사용자 -->
								<!-- 추가된 사용자 -->
								
							</tbody>
						</table>
						<div class="col-6 offset-md-1">
							<button class="btn btn-outline-secondary bg-gradient-dark btn-drive-save">저장</button>
							<button type="button" class="btn btn-outline-secondary" onclick="history.back()">취소</button>
						</div>
					</form:form>
				</div>

			</section>
		</div>
	</main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<c:import url="/WEB-INF/views/drive/modal.jsp"></c:import>
	<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"></script>
	<script src="/js/drive/create_modal.js"></script>
	<script>
	document.querySelector("i[data-content='드라이브']").parentElement.classList.add("bg-gradient-dark", "text-white")
	document.querySelector("#navTitle").textContent = "드라이브 추가"
	const loginStaffCode = ${ staffDTO.staffCode }
	</script>
</body>

</html>