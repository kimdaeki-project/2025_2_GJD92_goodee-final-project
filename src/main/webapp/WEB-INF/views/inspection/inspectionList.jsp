<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>어트랙션 점검</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <div class="d-flex">
    	<aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="height: 92vh;">
    		<div class="w-100">
			    <ul class="navbar-nav">
			    
			      <!-- 메뉴 개수만큼 추가 -->
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/ride">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="어트랙션 목록">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">어트랙션 목록</span>
			        </a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="삭제된 어트랙션 목록">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">삭제된 어트랙션 목록</span>
			        </a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/inspection">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="어트랙션 점검">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">어트랙션 점검</span>
			        </a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="어트랙션 고장 신고 목록">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">어트랙션 고장 신고 목록</span>
			        </a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="#">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="어트랙션 고장 신고">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">어트랙션 고장 신고</span>
			        </a>
			      </li>
			      
			      
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    
		    <!-- 여기에 코드 작성 -->
		    <h1>어트랙션 점검</h1>
		    <c:if test="${ totalInspection gt 0 }">
	    	<div>
	    	
	    	</div>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">어트랙션 코드</th>
						<th scope="col">점검번호</th>
						<th scope="col">점검유형</th>
						<th scope="col">점검결과</th>
						<th scope="col">담당자</th>
						<th scope="col">점검 시작일</th>
						<th scope="col">점검 종료일</th>
						<th scope="col">체크리스트</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ inspection.content }" var="i">
						<tr>
							<td scope="row">${ i.rideDTO.rideCode }</td>
							<td><a href="/inspection/${ i.isptNum }">${ i.isptNum }</a></td>
							
							
							
							<c:if test="${ i.isptType eq '401' }">
								<td>긴급점검</td>
							</c:if>
							<c:if test="${ i.isptType eq '501' }">
								<td>일일점검</td>
							</c:if>
							<c:if test="${ i.isptType eq '502' }">
								<td>정기점검</td>
							</c:if>
							
							<c:if test="${ i.isptResult eq '201' }">
								<td>정상</td>
							</c:if>
							<c:if test="${ i.isptResult eq '202' }">
								<td>특이사항 있음</td>
							</c:if>
							<c:if test="${ i.isptResult eq '203' }">
								<td>운영불가</td>
							</c:if>
							
							
							<td>${ i.staffDTO.staffName }</td>
							<td>${ i.isptStart }</td>
							<td>${ i.isptEnd }</td>
							<!--  체크리스트 -->
							<%-- <td>${ i. }</td> --%> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
<%-- 			<c:if test="${ notice.content.size() gt 0 }">
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
	    	</c:if> --%>
	    	</c:if>
		    
		    
		    
		    
		    
		    
		    <!-- 어트랙션 점검 기록 없음 -->
			<c:if test="${ totalInspection eq 0 }">
				<div>검색된 결과가 없습니다.</div>
			</c:if>    	
			
	    	<!-- 어트랙션 점검 기록 등록 -->
	    	<!-- 로그인 사용자 정보 꺼내기 -->
	    	<sec:authorize access="isAuthenticated()">
	     	  <sec:authentication property="principal" var="staff" />
			  
			  <!-- 시설부서(deptCode == 1003)일 때만 등록 버튼 보이기 -->
		      <c:if test="${staff.deptDTO.deptCode eq 1003}">
		        <div class="text-end mt-4 me-4">
		          <a href="${pageContext.request.contextPath}/inspection/write "
		             class="btn btn-primary btn-sm btn-outline-secondary bg-gradient-dark text-white me-3"
		             style="width: 100px;">등록</a>
		        </div>
		      </c:if>
		  </sec:authorize>
		    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script>
		document.querySelector("i[data-content='어트랙션']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='어트랙션 점검']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "어트랙션 점검"
	</script>
</body>

</html>