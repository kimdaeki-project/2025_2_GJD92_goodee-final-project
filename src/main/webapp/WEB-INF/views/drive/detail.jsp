<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
									<i class="material-symbols-rounded opacity-5 fs-5" >keyboard_arrow_down</i> 
									<span class="sub-nav-link-text ms-1 text-sm">내드라이브</span>
								</div>
								<div class="d-flex text-center">
									<a class="nav-link text-dark d-flex align-items-center" href="/drive/create">
										 <i class="material-symbols-rounded opacity-5 fs-5" >add</i>
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
								<span class="nav-link-text ms-1 text-sm">공용드라이브</span>
							</div>
							<ul class="navbar-nav" id="drive-share">
								<!-- 반복문 -->
								<c:import url="/WEB-INF/views/drive/side-shareDrive.jsp"></c:import>
							</ul>
						</li>
						<!-- 공용 드라이브 끝 -->
					</ul>
				</div>
			</aside>
			<section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3"
				style="height: 92vh; overflow: hidden scroll;">

				<!-- 여기에 코드 작성 -->
				<!-- 여기에 코드 작성 -->
				<!-- 여기에 코드 작성 -->
				<!-- 여기에 코드 작성 -->

			</section>
		</div>
	</main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
	document.querySelector("i[data-content='드라이브']").parentElement.classList.add("bg-gradient-dark", "text-white")
	document.querySelector("#navTitle").textContent = "드라이브"
	</script>
</body>

</html>