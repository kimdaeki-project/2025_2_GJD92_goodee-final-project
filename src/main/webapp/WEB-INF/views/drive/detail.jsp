<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>드라이브</title>
	
	<link rel="stylesheet" href="/css/drive/detail.css" />
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>

	<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
		<c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
		<div class="d-flex">
			<c:import url="/WEB-INF/views/drive/drive-sidebar.jsp"></c:import>
			<section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3 p-4">

				<!-- 여기에 코드 작성 -->
				<!-- 여기에 코드 작성 -->
				<!-- 툴바 -->
				<div class="d-flex align-items-center flex-wrap gap-2 mb-3">
				
				  <!-- 업로드 -->
				  <button type="button" class="btn btn-outline-secondary btn-sm d-flex align-items-center mb-0"
						   data-bs-toggle="modal" data-bs-target="#uploadModal">업로드</button>
				  
				  <!-- 새 문서 -->
<!-- 				  <button type="button" class="btn btn-outline-secondary btn-sm d-flex align-items-center mb-0">새 문서</button> -->
				
				  <!-- 파일 유형 -->
				  <div class="d-flex align-items-center gap-2">
				    <select class="form-select form-select-sm w-auto align-self-center rounded" id="fileTypeSelect">
				      <option value="" selected>파일 유형: 선택</option>
				      <option value="doc">문서</option>
				      <option value="image">이미지</option>
				      <option value="video">동영상</option>
				      <option value="audio">오디오</option>
				    </select>
				  </div>
				
				  <!-- 다운로드 -->
				  <button type="button" class="btn btn-outline-secondary btn-sm d-flex align-items-center justify-content-center mb-0"
				          id="downloadBtn">
				    <span class="material-symbols-rounded">download</span>
				  </button>
				
				  <!-- 삭제 -->
				  <button type="button" class="btn btn-outline-danger btn-sm d-flex align-items-center justify-content-center mb-0"
				          id="deleteBtn">
				    <span class="material-symbols-rounded">delete</span>
				  </button>
				</div>
					
					<div class="table-responsive">
					  <table class="table table-hover align-middle mb-0 table-figma drive-table">
					    <thead class="border-top">
					      <tr>
					        <th class="text-center" style="width:36px;"><input type="checkbox" id="checkAll"/></th>
					        <th>이름</th>
					        <th style="width:180px;">등록일</th>
					        <th style="width:120px;">용량</th>
					        <th style="width:120px;">종류</th>
					        <th style="width:140px;">등록자</th>
					      </tr>
					    </thead>
					    <tbody>
						  <c:choose>
						    <c:when test="${ not empty documents }">
						      <c:forEach var="doc" items="${ documents }">
						        <tr>
						          <td class="text-center"><input type="checkbox" value="${ doc.documentId }" /></td>
						          <td>${ doc.fileName }</td>
						          <td><fmt:formatDate value="${ doc.createdAt }" pattern="yyyy-MM-dd HH:mm"/></td>
						          <td>${ doc.size } KB</td>
						          <td>${ doc.type }</td>
						          <td>${ doc.uploader }</td>
						        </tr>
						      </c:forEach>
						    </c:when>
						    <c:otherwise>
						      <!-- 데이터 없을 때만 중앙 정렬 -->
						      <tr>
						        <td colspan="6" class="border-0">
						          <div class="d-flex justify-content-center align-items-center text-secondary" style="height:60vh;">
						            등록된 파일이 없습니다.
						          </div>
						        </td>
						      </tr>
						    </c:otherwise>
						  </c:choose>
						</tbody>
					  </table>
					</div>
					
			</section>
		</div>
	</main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<c:import url="/WEB-INF/views/drive/upload-modal.jsp"></c:import>
	<script>
	document.querySelector("i[data-content='드라이브']").parentElement.classList.add("bg-gradient-dark", "text-white")
	document.querySelector("i[data-content='${ driveDTO.driveName }']").parentElement.classList.add("bg-gradient-dark", "text-white")
	document.querySelector("#navTitle").textContent = "드라이브"
	</script>
	<script src="/js/drive/detail.js"></script>
</body>

</html>