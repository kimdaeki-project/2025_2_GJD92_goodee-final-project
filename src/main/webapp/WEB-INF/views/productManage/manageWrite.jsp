<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.form-box {
	background: #fff;
	padding: 40px;
	border-radius: 12px;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
	width: 400px;
	text-align: center;
}

.form-box h2 {
	margin-bottom: 30px;
	font-size: 20px;
	font-weight: bold;
}

.form-group {
	margin-bottom: 20px;
	text-align: left;
}

.form-group label {
	display: block;
	margin-bottom: 8px;
	font-weight: 500;
	font-size: 14px;
}

.form-group input[type="text"], .form-group input[type="file"] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 6px;
	font-size: 14px;
}

.form-group input[type="file"] {
	padding: 4px;
}

.btn-submit {
	background-color: #333;
	color: #fff;
	padding: 10px 30px;
	border: none;
	border-radius: 6px;
	font-size: 14px;
	cursor: pointer;
}

.btn-submit:hover {
	background-color: #555;
}

aside.sidenav {
	width: 200px !important; /* 원하는 값 (180~220px 정도 추천) */
	min-width: 200px !important;
	max-width: 200px !important;
}
.btn-close {
  background-color: red !important; /* 테스트용 - 보이는지 확인 */
  border: 1px solid black;
  width: 1.5rem;
  height: 1.5rem;
}
</style>
<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>

	<main
		class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
		<c:import url="/WEB-INF/views/common/nav.jsp"></c:import>

		<div class="d-flex">
			<aside
				class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start"
				style="width: 200px; height: 92vh;">
				<div class="w-100">
					<ul class="navbar-nav">

						<li class="nav-item"><a class="nav-link text-dark"
							href="/product"> <i
								class="material-symbols-rounded opacity-5 fs-5"
								data-content="물품 리스트">remove_shopping_cart</i> <span
								class="nav-link-text ms-1 text-sm">물품 리스트</span>
						</a></li>

						<li class="nav-item"><a class="nav-link text-dark"
							href="/productManage"> <i
								class="material-symbols-rounded opacity-5 fs-5"
								data-content="물품관리대장">remove_shopping_cart</i> <span
								class="nav-link-text ms-1 text-sm">물품관리대장</span>
						</a></li>

					</ul>
				</div>
			</aside>

			<section class="border-radius-xl bg-white ms-2 mt-2 me-3"
				style="height: 92vh; overflow: hidden scroll;">

				<div class="form-box">
					<h2>${empty productManageDTO.pmNum ? "입출고 등록" : "입출고 수정" }</h2>
					<form method="post" enctype="multipart/form-data">

						<div class="form-group">
						
							<button type="button"
								class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3"
								data-bs-toggle="modal" data-bs-target="#productModal"
								style="width: 100px;">물품검색</button>

							<label for="itemName">물품번호</label>
							<input type="text" name="productCode" value="${productManageDTO.productDTO.productCode }" readonly required placeholder="물품을 검색해주세요.">
								
							<input type="hidden" name="productTypeDTO.productTypeCode" value="${productManageDTO.productDTO.productTypeDTO.productTypeCode }"/>
							
							<label for="itemName">물품타입</label>
							<input type="text" name="productTypeDTO.productTypeName" value="${productManageDTO.productDTO.productTypeDTO.productTypeName }" readonly required placeholder="물품을 검색해주세요.">
								 
							<label for="itemName">물품명</label>
							<input type="text" name="productName" value="${productManageDTO.productDTO.productName }" readonly required placeholder="물품을 검색해주세요.">
							
						</div>

						<div class="form-group">
							<label>유형</label>
							<div class="radio-group">
								<label>
									<label>
							            <input type="radio" name="pmType" value="80"
							                <c:if test="${empty productManageDTO or productManageDTO.pmType == 80}">checked</c:if>> 입고
							        </label> 
								<label>
									<input type="radio"	name="pmType" value="90"
										<c:if test="${productManageDTO.pmType == 90}">checked</c:if>> 출고
								</label>
							</div>
						</div>

						<div class="form-group">
							<label>수량</label>
							<input type="text" name="pmAmount" value="${productManageDTO.pmAmount }">
						</div>
						
						<div class="form-group">
							<label>비고</label>
							<input type="text" name="pmNote" value="${productManageDTO.pmNote }">
						</div>

						<button type="submit"
							class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3"
							style="width: 100px;">${ empty productManageDTO.pmNum ? "등록" : "수정" }</button>
						<button type="button" class="btn btn-sm btn-outline-secondary"
							onclick="history.back();" style="width: 100px;">취소</button>
					</form>
				</div>

				<!-- 모달창 내용 -->
				<div class="modal fade" id="productModal" tabindex="-1"
					aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">물품 목록</h5>
								<div class="d-flex justify-content-end align-items-end">
									<div class="input-group">
										<input type="text" class="form-control" id="searchInput" style="width: 200px; height: 30px; border-radius: 0.375rem 0 0 0.375rem !important;" >
									</div>
								</div>
								<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<table class="table">
									<thead>
										<tr>
											<th>물품번호</th>
											<th>물품타입</th>
											<th>물품명</th>
											<th>선택</th>
										</tr>
									</thead>
									<tbody id="productTable">
									
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

			</section>
		</div>
	</main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/productManage/list.js"></script>
	<script>
		document.querySelector("i[data-content='재고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='물품관리대장']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "물품관리대장"
	</script>
</body>


</html>