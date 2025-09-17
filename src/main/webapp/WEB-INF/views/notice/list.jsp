<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>공지사항</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>

	<style>
		/* 테이블 마지막 줄 경계선 보정 */
		.table tbody tr:last-child td,
		.table tbody tr:last-child th {
			border-bottom: 1px solid #dee2e6;
		}
	</style>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3 p-4" style="height: 90vh; overflow: hidden scroll;">
    	
    	<!-- 검색창 -->
    	<form action="/notice" class="mb-4">
		    <div class="d-flex justify-content-end">
		        <div class="input-group w-25">
		            <input type="text" class="form-control" placeholder="제목 또는 작성자로 검색" name="keyword" value="${ pager.keyword }">
		            <button class="btn btn-dark" type="submit">검색</button>
		        </div>
		    </div>
		</form>
    
    	<!-- 공지 존재 -->
    	<c:if test="${ totalNotice gt 0 }">
		<table class="table table-hover align-middle text-center">
			<thead class="table-light">
				<tr>
					<th scope="col">No</th>
					<th scope="col">부서</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">작성일</th>
					<th scope="col">조회수</th>
				</tr>
			</thead>
			<tbody>
				<!-- 고정공지 -->
				<c:if test="${ notice.number eq 0 }">
				<c:forEach items="${ pinned }" var="n">
					<tr class="table-info fw-bold">
						<th scope="row">&#x1F4E2;</th>
						<td>${ n.staffDTO.deptDTO.deptDetail }팀</td>
						<td class="text-start"><a class="text-decoration-none" href="/notice/${ n.noticeNum }">${ n.noticeTitle }</a></td>
						<td>${ n.staffDTO.staffName }</td>
						<td>${ n.noticeDate }</td>
						<td>${ n.noticeHits }</td>
					</tr>
				</c:forEach>
				</c:if>
				
				<!-- 일반공지 -->
				<c:forEach items="${ notice.content }" var="n">
					<tr>
						<th scope="row">${ n.noticeNum }</th>
						<td>${ n.staffDTO.deptDTO.deptDetail }팀</td>
						<td class="text-start"><a class="text-decoration-none" href="/notice/${ n.noticeNum }">${ n.noticeTitle }</a></td>
						<td>${ n.staffDTO.staffName }</td>
						<td>${ n.noticeDate }</td>
						<td>${ n.noticeHits }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 페이지네이션 -->
		<c:if test="${ notice.content.size() gt 0 }">
		<nav>
			<ul class="pagination justify-content-center">
				<c:if test="${ notice.hasPrevious() and pager.startPage gt 1 }">
					<li class="page-item">
						<a class="page-link" href="?page=${ pager.startPage - 1 }&keyword=${ pager.keyword }">이전</a>
					</li>
				</c:if>
				
				<c:forEach var="i" begin="${ pager.startPage }" end="${ pager.endPage }">
					<li class="page-item ${ i == notice.number ? 'active' : '' }">
						<a class="page-link" href="?page=${i}&keyword=${ pager.keyword }">${i + 1}</a>
					</li>
				</c:forEach>
				
				<c:if test="${ notice.hasNext() and pager.endPage + 1 ne notice.totalPages }">
					<li class="page-item">
						<a class="page-link" href="?page=${ pager.endPage + 1 }&keyword=${ pager.keyword }">다음</a>
					</li>
				</c:if>
			</ul>
		</nav>
    	</c:if>
    	</c:if>
    	<!-- 공지 존재 -->
    	
    	<!-- 공지 없음 -->
		<c:if test="${ totalNotice eq 0 }">
			<div class="alert alert-secondary text-center">검색된 결과가 없습니다.</div>
		</c:if>    	
    	<!-- 공지 없음 -->
    	
    	<!-- 작성 버튼 -->
    	<div class="d-flex justify-content-end mt-3">
    		<a href="/notice/write" class="btn bg-gradient-dark">작성</a>
    	</div>
    	
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='공지사항']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "공지사항"
	</script>
</body>

</html>

