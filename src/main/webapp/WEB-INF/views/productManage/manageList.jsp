<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>물품관리대장</title>
	
	<style type="text/css">
	
	aside.sidenav {
	  width: 225px !important;   /* 원하는 값 (180~220px 정도 추천) */
	  min-width: 200px !important;
	  max-width: 200px !important;
	}
	table tbody tr:hover {
		cursor: pointer;
    }
	.pagination li:hover {
		cursor: pointer;
    }
	
	</style>
	
	<c:import url="/WEB-INF/views/common/header.jsp"></c:import>
</head>

<body class="g-sidenav-show bg-gray-100">
	<c:import url="/WEB-INF/views/common/sidebar.jsp"></c:import>
  
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <c:import url="/WEB-INF/views/common/nav.jsp"></c:import>
    <div class="d-flex">
    <aside class="sidenav navbar navbar-vertical border-radius-lg ms-2 bg-white my-2 w-10 align-items-start" style="width: 200px; height: 92vh;">
    		<div class="w-100">
			    <ul class="navbar-nav">
			    
			    	<li class="nav-item">
			        <a class="nav-link text-dark" href="/product">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="물품 리스트">remove_shopping_cart</i>
			          <span class="nav-link-text ms-1 text-sm">물품 리스트</span>
			        </a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link text-dark" href="/productManage">
			          <i class="material-symbols-rounded opacity-5 fs-5" data-content="물품관리대장">remove_shopping_cart</i>
			          <span class="nav-link-text ms-1 text-sm">물품관리대장</span>
			        </a>
			      </li>
			      
			    </ul>
			  </div>
    	</aside>
    	
	    <section class="flex-grow-1 border-radius-xl bg-white ms-2 mt-2 me-3" style="height: 92vh ; overflow: auto;">
	    
		
	    <div class="mt-3" style="min-height: 500px;">
	    	<div class="col-10 offset-1">
	    	
			    <div class="d-flex justify-content-between align-items-end mt-4 mb-4">
		    		<div>총 &nbsp;${productManageList.totalElements } 건</div>
					<div class="input-group w-25">
						<input type="text" class="form-control" id="searchText" value="${ searchKeyword }" style="width: 200px; height: 30px; border-radius: 0.375rem 0 0 0.375rem !important;" >
						<button class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white p-0 m-0" type="button" onclick="movePage()" style="width: 50px; height: 30px;" >검색</button>
					</div>
				</div>
				
	    		<table class="table table-hover text-center">
	    			<thead>
	    				<tr>
	    					<th class="col-1">No.</th>
	    					<th class="col-1">입출고일자</th>
<!-- 			    					<th class="col-1">물품타입</th> -->
<!-- 			    					<th>물품코드</th> -->
	    					<th class="col-2">물품명</th>
	    					<th class="col-1">구분</th>
	    					<th class="col-1">등록수량</th>
<!-- 			    					<th>입고</th> -->
<!-- 			    					<th>출고</th> -->
	    					<th class="col-1">잔여수량</th>
	    					<th class="col-1">작성자</th>
	    					<th class="col-1">비고</th>
	    				</tr>
	    			</thead>
	    			<tbody>
	    				
	    				<c:forEach var="pm" items="${ productManageList.content }">
	    					<tr>
		    					<td>${ pm.pmNum }</td>
		    					<td>${ pm.pmDate }</td>
<%-- 				    					<td>${ pm.productDTO.productTypeDTO.productTypeName }</td> --%>
<%-- 				    					<td>${ pm.productDTO.productCode }</td> --%>
		    					<td><a data-bs-toggle="modal" data-bs-target="#pmDetailModal" href="/productManage/${ pm.pmNum }" style="color: #737373;">${ pm.productDTO.productName }</a></td>
<%-- 				    					<td>${ pm.pmType eq 80 ? pm.pmAmount : "-" }</td> --%>
<%-- 				    					<td>${ pm.pmType eq 90 ? pm.pmAmount : "-" }</td> --%>
		    					<td>${ pm.pmType eq 90 ? "출고" : "입고" }</td>
		    					<td><fmt:formatNumber value="${ pm.pmAmount }" type="number" /></td>
								<td><fmt:formatNumber value="${ pm.pmRemainAmount }" type="number" /></td>
		    					<td>${ pm.staffDTO.staffName }</td>
		    					<td class="text-start">${ pm.pmNote }</td>
	    					</tr>
	    				</c:forEach>
	    				
	    			</tbody>
	    		</table>
	    		
			    <c:if test="${ totalProductManage eq 0 }">
					 <div class="alert alert-secondary text-center" style="color: white;">검색된 결과가 없습니다.</div>
				</c:if>
		
		   		<div class="d-flex justify-content-center aling-items-center">
			    	<nav aria-label="Page navigation example">
					  <ul class="pagination">
					    <c:if test="${ productManageList.number - 2 ge 0 }">
						    <li class="page-item">
						      <a class="page-link" onclick="movePage('${ productManageList.number - 2 }')" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
					    </c:if>
					    <c:if test="${ productManageList.hasPrevious() }">
						    <li class="page-item"><a class="page-link" onclick="movePage('${ productManageList.number - 1 }')">${ productManageList.number }</a></li>
					    </c:if>
					    <li class="page-item"><a class="page-link active" onclick="movePage('${ productManageList.number }')">${ productManageList.number + 1 }</a></li>
					    <c:if test="${ productManageList.hasNext() }">
						    <li class="page-item"><a class="page-link" onclick="movePage('${ productManageList.number + 1 }')">${ productManageList.number + 2 }</a></li>
					    </c:if>
					    <c:if test="${ productManageList.number + 2 le productManageList.totalPages - 1 }">
						    <li class="page-item">
						      <a class="page-link" onclick="movePage('${ productManageList.number + 2 }')" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
					    </c:if>
					  </ul>
					</nav>
			    </div>
				
				<div class="d-flex justify-content-end aling-items-end">
					<div>
		    			<button class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3" onclick="location.href='/productManage/write'">입출고등록</button>
		   			</div>
	  			</div>
	    	</div>
	    </div>
			    
		<!-- 모달창 내용 -->
	<div class="modal fade" id="pmDetailModal" tabindex="-1" aria-hidden="true">	    
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">입출고내역 상세</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				
				<div class="modal-body">
					<div class="text-center mb-5" id="pmDetailAttach">
					
					</div>
					<div class="d-flex justify-content-center">
						<table class="text-start table" style="width: 80%;">
							
							<tbody id="pmDetailTable">
							
							</tbody>
						</table>
					</div>
					
					<div class="mt-4 d-flex justify-content-center gap-1 d-none" id="pmModalButtons" style="display: none;">
						<button id="pmUpdateBtn" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">수정</button>
  						<form id="pmDeleteForm" method="post">
						    <button type="submit" class="btn btn-sm btn-outline-secondary bg-gradient-dark text-white me-3">삭제</button>
						</form>
					</div>
					
				</div>
			</div>
		</div>
	</div>
		
	    </section>
    </div>
  </main>
	<c:import url="/WEB-INF/views/common/footer.jsp"></c:import>
	<script src="/js/productManage/list.js"></script>
	<script src="/js/productManage/detail.js"></script>
	<script>
		document.querySelector("i[data-content='재고']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("i[data-content='물품관리대장']").parentElement.classList.add("bg-gradient-dark", "text-white")
		document.querySelector("#navTitle").textContent = "물품관리대장"
	</script>
	<script>
	const loginStaffCode = "${staffDTO.staffCode}";
	</script>
</body>

</html>