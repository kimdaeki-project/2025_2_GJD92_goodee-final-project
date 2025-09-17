<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>공지사항</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 90vh; overflow: hidden scroll;">
    	
    	<form action="/notice">
	    	<div class="input-group">
				<input type="text" class="form-control" placeholder="제목 또는 작성자로 검색" name="keyword" value="${ pager.keyword }">
				<button class="btn btn-outline-secondary">검색</button>
			</div>   	
    	</form>
    
    	<!-- 공지 존재 -->
    	<c:if test="${ totalNotice gt 0 }">
    	<div>
    	
    	</div>
		<table class="table">
			<thead>
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
				<c:if test="${ notice.number eq 0 }">
				<c:forEach items="${ pinned }" var="n">
					<tr class="table-info">
						<th scope="row">&#x1F4E2;</th>
						<c:if test="${ n.staffDTO.deptDTO.deptName eq 'ROLE_HR' }">
							<td>인사팀</td>
						</c:if>
						<c:if test="${ n.staffDTO.deptDTO.deptName eq 'ROLE_OP' }">
							<td>운영팀</td>
						</c:if>
						<c:if test="${ n.staffDTO.deptDTO.deptName eq 'ROLE_FA' }">
							<td>시설팀</td>
						</c:if>
						<td><a href="/notice/${ n.noticeNum }">${ n.noticeTitle }</a></td>
						<td>${ n.staffDTO.staffName }</td>
						<td>${ n.noticeDate }</td>
						<td>${ n.noticeHits }</td>
					</tr>
				</c:forEach>
				</c:if>
				<c:forEach items="${ notice.content }" var="n">
					<tr>
						<th scope="row">${ n.noticeNum }</th>
						<c:if test="${ n.staffDTO.deptDTO.deptName eq 'ROLE_HR' }">
							<td>인사팀</td>
						</c:if>
						<c:if test="${ n.staffDTO.deptDTO.deptName eq 'ROLE_OP' }">
							<td>운영팀</td>
						</c:if>
						<c:if test="${ n.staffDTO.deptDTO.deptName eq 'ROLE_FA' }">
							<td>시설팀</td>
						</c:if>
						<td><a href="/notice/${ n.noticeNum }">${ n.noticeTitle }</a></td>
						<td>${ n.staffDTO.staffName }</td>
						<td>${ n.noticeDate }</td>
						<td>${ n.noticeHits }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${ notice.content.size() gt 0 }">
		<div>
			<c:if test="${ notice.hasPrevious() }">
	    		<a href="?page=${ notice.number - 1 }&keyword=${ pager.keyword }">이전</a>
			</c:if>
			<c:forEach var="i" begin="${ pager.startPage }" end="${ pager.endPage }">
	    		<a href="?page=${i}&keyword=${ pager.keyword }" style="${ i == notice.number ? 'font-weight:bold;' : '' }">
	        		${i + 1}
	    		</a>
			</c:forEach>
			<c:if test="${ notice.hasNext() }">
	    		<a href="?page=${ notice.number + 1 }&keyword=${ pager.keyword }">다음</a>
			</c:if>
    	</div>
    	</c:if>
    	</c:if>
    	<!-- 공지 존재 -->
    	
    	<!-- 공지 없음 -->
		<c:if test="${ totalNotice eq 0 }">
			<div>검색된 결과가 없습니다.</div>
		</c:if>    	
    	<!-- 공지 없음 -->
    	<div>
    		<a href="/notice/write">작성</a>
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