<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <section class="border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh; overflow: hidden scroll;">
    
    <div class="d-flex justify-content-end align-items-end">
		<div class="input-group">
			<input type="text" class="form-control" id="searchText" value="${ requestScope.search }" style="width: 200px; height: 30px; border-radius: 0.375rem 0 0 0.375rem !important;" >
			<button class="btn btn-outline-secondary p-0 m-0" type="button" onclick="movePage()" style="width: 50px; height: 30px;" >검색</button>
		</div>
	</div>
	
    <div class="mt-3" style="min-height: 500px;">
		    	<div class="col-10 offset-1">
		    		<table class="table table-hover text-center">
		    			<thead>
		    				<tr>
		    					<th>분실물번호</th>
		    					<th>분실물명</th>
		    					<th>작성자</th>
		    					<th>연락처</th>
		    					<th>등록일자</th>
		    				</tr>
		    			</thead>
		    			<tbody>
		    				
		    				<c:forEach var="lost" items="${ lostList.content }">
		    					<tr>
			    					<td>${ lost.lostNum }</td>
			    					<td><a href="/lost/${ lost.lostNum }" style="color: #737373;">${ lost.lostName }</a></td>
			    					<td>${ lost.staffDTO.staffName }</td>
			    					<td>${ lost.staffDTO.staffPhone }</td>
			    					<td>${ lost.lostDate }</td>
		    					</tr>
		    				</c:forEach>
		    				
		    			</tbody>
		    		</table>
		    
		     <c:if test="${ totalLost eq 0 }">
		        <div class="alert alert-secondary text-center">검색된 결과가 없습니다.</div>
		    </c:if>
		    
		    	</div>
		    </div>
		    
    <div class="d-flex justify-content-center aling-items-center">
		    	<nav aria-label="Page navigation example">
					  <ul class="pagination">
					    <c:if test="${ lostList.number - 2 ge 0 }">
						    <li class="page-item">
						      <a class="page-link" onclick="movePage('${ lostList.number - 2 }')" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
					    </c:if>
					    <c:if test="${ lostList.hasPrevious() }">
						    <li class="page-item"><a class="page-link" onclick="movePage('${ lostList.number - 1 }')">${ lostList.number }</a></li>
					    </c:if>
					    <li class="page-item"><a class="page-link active" onclick="movePage('${ lostList.number }')">${ lostList.number + 1 }</a></li>
					    <c:if test="${ lostList.hasNext() }">
						    <li class="page-item"><a class="page-link" onclick="movePage('${ lostList.number + 1 }')">${ lostList.number + 2 }</a></li>
					    </c:if>
					    <c:if test="${ lostList.number + 2 le lostList.totalPages - 1 }">
						    <li class="page-item">
						      <a class="page-link" onclick="movePage('${ lostList.number + 2 }')" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
					    </c:if>
					  </ul>
					</nav>
		    </div>
			
		    <button onclick="location.href='/lost/write'" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">등록</button>
    </section>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/lost/list.js"></script>
	<script>
		document.querySelector("i[data-content='분실물']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "분실물"
	</script>
</body>

</html>