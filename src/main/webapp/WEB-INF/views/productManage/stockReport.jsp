<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>재고 집계표</title>
<style type="text/css">

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

aside.sidenav {
	width: 200px !important; /* 원하는 값 (180~220px 정도 추천) */
	min-width: 200px !important;
	max-width: 200px !important;
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
						
						<li class="nav-item"><a class="nav-link text-dark"
							href="/productManage/stockReport"> <i
								class="material-symbols-rounded opacity-5 fs-5"
								data-content="재고집계표">remove_shopping_cart</i> <span
								class="nav-link-text ms-1 text-sm">재고집계표</span>
						</a></li>

					</ul>
				</div>
			</aside>

			<section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; width: 100%; overflow: hidden;">

  <!-- 검색 조건 -->
  <div style="padding: 20px; background-color: #f9f9f9; border: 1px solid #ddd; border-radius: 6px; display: flex; gap: 16px; align-items: center; flex-wrap: wrap; font-family: sans-serif;">

  <div style="display: flex; flex-direction: column; font-size: 12px;">
    <label for="start-date">시작일</label>
    <input type="date" id="start-date" name="start-date" style="padding: 4px;">
  </div>

  <div style="display: flex; flex-direction: column; font-size: 12px;">
    <label for="end-date">종료일</label>
    <input type="date" id="end-date" name="end-date" style="padding: 4px;">
  </div>

  <div style="display: flex; flex-direction: column; font-size: 12px;">
    <label for="type">타입</label>
    <select id="type" name="type" style="padding: 4px;">
      <option value="all">전체</option>
      <option value="type1">타입 1</option>
      <option value="type2">타입 2</option>
    </select>
  </div>

  <div style="display: flex; flex-direction: column; font-size: 12px;">
    <label for="category">카테고리</label>
    <select id="category" name="category" style="padding: 4px;">
      <option value="">--선택--</option>
      <option value="cat1">카테고리 1</option>
      <option value="cat2">카테고리 2</option>
    </select>
  </div>

  <div style="display: flex; flex-direction: column; font-size: 12px;">
    <label for="item-name">물품명</label>
    <input type="text" id="item-name" name="item-name" placeholder="물품명 입력" style="padding: 4px;">
  </div>

  <div style="margin-top: 20px;">
    <button style="padding: 8px 16px; background-color: black; color: white; border: none; border-radius: 4px; cursor: pointer;">
      조회
    </button>
  </div>

</div>



  <!-- 결과 테이블 -->
  <table class="table table-bordered text-center align-middle ms-2 me-4">
    <thead class="table-dark">
      <tr>
        <th>물품명</th>
        <th>타입</th>
        <th>입고 수량</th>
        <th>출고 수량</th>
        <th>잔여 수량</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="s" items="${summaryList}">
        <tr>
          <td>${s.productName}</td>
          <td>${s.productTypeName}</td>
          <td><fmt:formatNumber value="${s.inAmount}" /></td>
          <td><fmt:formatNumber value="${s.outAmount}" /></td>
          <td><fmt:formatNumber value="${s.remain}" /></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
			

			</section>
		</div>
	</main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/productManage/write.js"></script>
	<script>
		document.querySelector("i[data-content='재고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='재고집계표']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "재고집계표"
	</script>
</body>


</html>