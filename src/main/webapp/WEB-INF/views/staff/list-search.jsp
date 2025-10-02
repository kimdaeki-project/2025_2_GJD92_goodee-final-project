<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>사원</title>
	
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
			    
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/regist">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="사원 등록">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">사원 등록</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="사원 조회">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">사원 조회</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/leave?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="연차 현황">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">연차 현황</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/quit?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="퇴사자 조회">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">퇴사자 조회</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/vacation?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="휴가 사용 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">휴가 사용 내역</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/overtime?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="연장근무 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">연장근무 내역</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/staff/early?page=0">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="조기 퇴근 내역">diversity_3</i>
			          <span class="nav-link-text ms-1 text-sm">조기 퇴근 내역</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
	    <section class="border-radius-xl bg-white w-90 ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
	    	<div class="mt-5">
	    		<div class="col-10 offset-1 d-flex justify-content-end">
	    			<div class="d-flex justify-content-end align-items-end">
    					<div class="input-group">
							  <input type="text" class="form-control" id="searchText" value="${ requestScope.search }" style="width: 200px; height: 30px; border-radius: 0.375rem 0 0 0.375rem !important;" >
							  <button class="btn btn-outline-secondary p-0 m-0" type="button" onclick="movePage()" style="width: 50px; height: 30px;" >검색</button>
							</div>
	    			</div>
	    		</div>
	    	</div>
	    
		    <div class="mt-3" style="min-height: 500px;">
		    	<div class="col-10 offset-1">
		    		<table class="table text-center">
		    			<c:if test="${ searchCode eq 'vacation' }">
		    				
		    				<thead>
			    				<tr>
			    					<th class="col-1">번호</th>
			    					<th class="col-2">이름</th>
			    					<th class="col-1">부서</th>
			    					<th class="col-1">직위</th>
			    					<th class="col-1">종류</th>
			    					<th class="col-3">기간</th>
			    					<th class="col-1">비고</th>
			    				</tr>
			    			</thead>
			    			<tbody>
			    				
			    				<c:forEach var="staff" items="${ staffList.content }">
			    					<tr>
			    						<fmt:parseDate value="${ staff.vacStart }" pattern="yyyy-MM-dd" var="parsedVacStart" type="both" />
			    						<fmt:parseDate value="${ staff.vacEnd }" pattern="yyyy-MM-dd" var="parsedVacEnd" type="both" />
			    					
				    					<td>${ staff.vacNum }</td>
				    					<td>${ staff.staffName }</td>
				    					<td>${ staff.deptDetail }</td>
				    					<td>${ staff.jobDetail }</td>
				    					<td>
				    						<c:if test="${ staff.vacType eq 9010 }">연차</c:if>
				    						<c:if test="${ staff.vacType eq 9011 }">공가</c:if>
				    						<c:if test="${ staff.vacType eq 9012 }">병가</c:if>
				    					</td>
				    					<td><fmt:formatDate pattern="yyyy년 MM월 dd일" value="${ parsedVacStart }"/>  ~ <fmt:formatDate pattern="yyyy년 MM월 dd일" value="${ parsedVacEnd }"/></td>
				    					<td><a href="/approval/${ staff.aprvCode }" style="color: #737373;">문서조회</a></td>
			    					</tr>
			    				</c:forEach>
			    				
			    			</tbody>
		    				
		    			</c:if>
		    			
		    			<c:if test="${ searchCode eq 'overtime' }">
		    			
		    				<thead>
			    				<tr>
			    					<th class="col-1">번호</th>
			    					<th class="col-2">이름</th>
			    					<th class="col-1">부서</th>
			    					<th class="col-1">직위</th>
			    					<th class="col-2">시작시간</th>
			    					<th class="col-2">종료시간</th>
			    					<th class="col-1">비고</th>
			    				</tr>
			    			</thead>
			    			<tbody>
			    				
			    				<c:forEach var="staff" items="${ staffList.content }">
			    					<tr>
			    						<fmt:parseDate value="${ staff.overStart }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedOverStart" type="both" />
			    						<fmt:parseDate value="${ staff.overEnd }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedOverEnd" type="both" />
			    					
			    						<td>${ staff.overNum }</td>
				    					<td>${ staff.staffName }</td>
				    					<td>${ staff.deptDetail }</td>
				    					<td>${ staff.jobDetail }</td>
				    					<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시 mm분" value="${ parsedOverStart }"/></td>
				    					<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시 mm분" value="${ parsedOverEnd }"/></td>
				    					<td><a href="/approval/${ staff.aprvCode }" style="color: #737373;">문서조회</a></td>
			    					</tr>
			    				</c:forEach>
			    				
			    			</tbody>
		    				
		    			</c:if>
		    			
		    			<c:if test="${ searchCode eq 'early' }">
		    			
		    				<thead>
			    				<tr>
			    					<th class="col-1">번호</th>
			    					<th class="col-2">이름</th>
			    					<th class="col-1">부서</th>
			    					<th class="col-1">직위</th>
			    					<th class="col-1">종류</th>
			    					<th class="col-2">조퇴시간</th>
			    					<th class="col-1">비고</th>
			    				</tr>
			    			</thead>
			    			<tbody>
			    				
			    				<c:forEach var="staff" items="${ staffList.content }">
			    					<tr>
			    						<fmt:parseDate value="${ staff.earlyDtm }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedEarlyDtm" type="both" />
			    						
			    						<td>${ staff.earlyNum }</td>
				    					<td>${ staff.staffName }</td>
				    					<td>${ staff.deptDetail }</td>
				    					<td>${ staff.jobDetail }</td>
				    					<td>
				    						<c:if test="${ staff.earlyType eq 9030 }">질병</c:if>
				    						<c:if test="${ staff.earlyType eq 9031 }">경조사</c:if>
				    						<c:if test="${ staff.earlyType eq 9032 }">외부행사</c:if>
				    						<c:if test="${ staff.earlyType eq 9039 }">기타</c:if>
				    					</td>
				    					<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시 mm분" value="${ parsedEarlyDtm }"/></td>
				    					<td><a href="/approval/${ staff.aprvCode }" style="color: #737373;">문서조회</a></td>
			    					</tr>
			    				</c:forEach>
			    				
			    			</tbody>
		    			
		    			</c:if>
		    		
		    		
		    			
		    		</table>
		    	</div>
		    </div>
		    
		    <div class="d-flex justify-content-center aling-items-center">
		    	<nav aria-label="Page navigation example">
					  <ul class="pagination">
					    <c:if test="${ staffList.number - 2 ge 0 }">
						    <li class="page-item">
						      <a class="page-link" onclick="movePage('${ staffList.number - 2 }')" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
					    </c:if>
					    <c:if test="${ staffList.hasPrevious() }">
						    <li class="page-item"><a class="page-link" onclick="movePage('${ staffList.number - 1 }')">${ staffList.number }</a></li>
					    </c:if>
					    <li class="page-item"><a class="page-link active" onclick="movePage('${ staffList.number }')">${ staffList.number + 1 }</a></li>
					    <c:if test="${ staffList.hasNext() }">
						    <li class="page-item"><a class="page-link" onclick="movePage('${ staffList.number + 1 }')">${ staffList.number + 2 }</a></li>
					    </c:if>
					    <c:if test="${ staffList.number + 2 le staffList.totalPages - 1 }">
						    <li class="page-item">
						      <a class="page-link" onclick="movePage('${ staffList.number + 2 }')" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
					    </c:if>
					  </ul>
					</nav>
		    </div>
	    
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/staff/list-search.js"></script>
	<script>
		const searchCode = "${ searchCode }"
		const searchType = "${ searchType }"
	
		document.querySelector("i[data-content='사원']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='${ searchType }']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = searchType
	</script>
</body>

</html>